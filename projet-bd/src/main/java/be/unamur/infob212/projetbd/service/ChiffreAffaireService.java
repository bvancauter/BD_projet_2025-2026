package be.unamur.infob212.projetbd.service;

import be.unamur.infob212.projetbd.dto.ChiffreAffaire.CaAnnuelDTO;
import be.unamur.infob212.projetbd.dto.ChiffreAffaire.CaMensuelDTO;
import be.unamur.infob212.projetbd.repository.ChiffreAffaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ChiffreAffaireService {

    private final ChiffreAffaireRepository dao;


    public List<CaMensuelDTO> getMensuel() {
        return dao.getCaMensuel();
    }

    public List<CaAnnuelDTO> getAnnuel() {
        return dao.getCaAnnuel();
    }
}
