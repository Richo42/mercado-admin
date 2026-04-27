package com.mercado.admin.service;

import com.mercado.admin.dto.ReciboDTO;
import com.mercado.admin.entity.Cuota;
import com.mercado.admin.entity.Puesto;
import com.mercado.admin.entity.Recibo;
import com.mercado.admin.repository.CuotaRepository;
import com.mercado.admin.repository.PuestoRepository;
import com.mercado.admin.repository.ReciboRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Unidad 2.3.4: Estereotipos Spring
 * @Service marca esta clase como componente de lógica de negocio.
 * Spring la detectará automáticamente y permitirá inyectarla en los controladores.
 */
@Service
@RequiredArgsConstructor
public class ReciboService {

    private final ReciboRepository reciboRepository;
    private final CuotaRepository cuotaRepository;
    private final PuestoRepository puestoRepository;

    /**
     * @Transactional garantiza que todas las operaciones de base de datos
     * se ejecuten como una sola unidad atómica. Si falla la generación de cuotas,
     * el recibo no se guarda y la base de datos vuelve a su estado anterior.
     */
    @Transactional
    public ReciboDTO crearRecibo(ReciboDTO dto) {
        Recibo recibo = new Recibo();
        recibo.setTipo(dto.tipo());
        recibo.setCategoria(dto.categoria());
        recibo.setMontoTotal(dto.montoTotal());
        recibo.setFechaEmision(dto.fechaEmision());
        recibo.setFechaVencimiento(dto.fechaVencimiento());
        recibo.setArchivoAdjunto(dto.archivoAdjunto());

        recibo = reciboRepository.save(recibo);

        String periodo = recibo.getFechaEmision().format(DateTimeFormatter.ofPattern("yyyy-MM"));

        if ("GENERAL".equalsIgnoreCase(dto.categoria())) {
            generarCuotasGenerales(recibo, periodo);
        } else if ("INDIVIDUAL".equalsIgnoreCase(dto.categoria())) {
            if (dto.puestoCodigo() == null || dto.puestoCodigo().isBlank()) {
                throw new IllegalArgumentException("Para gastos INDIVIDUALES debe enviar 'puestoCodigo'");
            }
            generarCuotaIndividual(recibo, dto.puestoCodigo(), periodo);
        }

        return new ReciboDTO(
                recibo.getId(),
                recibo.getTipo(),
                recibo.getCategoria(),
                recibo.getMontoTotal(),
                recibo.getFechaEmision(),
                recibo.getFechaVencimiento(),
                recibo.getArchivoAdjunto(),
                dto.puestoCodigo()
        );
    }

    private void generarCuotasGenerales(Recibo recibo, String periodo) {
        List<Puesto> puestos = puestoRepository.findAll();
        if (puestos.isEmpty()) return;

        BigDecimal montoPorPuesto = recibo.getMontoTotal()
                .divide(BigDecimal.valueOf(puestos.size()), 2, RoundingMode.HALF_UP);

        for (Puesto puesto : puestos) {
            Cuota cuota = new Cuota();
            cuota.setPuesto(puesto);
            cuota.setRecibo(recibo);
            cuota.setMonto(montoPorPuesto);
            cuota.setEstado("PENDIENTE");
            cuota.setPeriodo(periodo);
            cuotaRepository.save(cuota);
        }
    }

    private void generarCuotaIndividual(Recibo recibo, String puestoCodigo, String periodo) {
        Puesto puesto = puestoRepository.findById(puestoCodigo)
                .orElseThrow(() -> new IllegalArgumentException("Puesto no encontrado: " + puestoCodigo));

        Cuota cuota = new Cuota();
        cuota.setPuesto(puesto);
        cuota.setRecibo(recibo);
        cuota.setMonto(recibo.getMontoTotal());
        cuota.setEstado("PENDIENTE");
        cuota.setPeriodo(periodo);
        cuotaRepository.save(cuota);
    }

    public List<Recibo> obtenerTodos() {
        return reciboRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Recibo> listarPorPuesto(String puestoCodigo) {
        return cuotaRepository.findRecibosByPuestoCodigo(puestoCodigo);
    }
}
