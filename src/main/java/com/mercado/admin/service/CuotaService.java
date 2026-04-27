package com.mercado.admin.service;

import com.mercado.admin.entity.Cuota;
import com.mercado.admin.repository.CuotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CuotaService {

    private final CuotaRepository cuotaRepository;

    @Transactional(readOnly = true)
    public List<Cuota> obtenerDeudasPorPuesto(String codigoPuesto) {
        return cuotaRepository.findByPuestoCodigo(codigoPuesto);
    }
}