package be.unamur.infob212.projetbd.service;

import be.unamur.infob212.projetbd.dto.Rapport.ArticlePopulaireDTO;
import be.unamur.infob212.projetbd.dto.Rapport.ClientActifDTO;
import be.unamur.infob212.projetbd.dto.Rapport.VueRemboursementDTO;
import be.unamur.infob212.projetbd.repository.RapportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RapportService {

    private final RapportRepository rapportRepository;

    public List<ArticlePopulaireDTO> getArticlesPopulaires() {
        return rapportRepository.getArticlesPopulaires();
    }

    public List<ClientActifDTO> getClientsActifs() {
        return rapportRepository.getClientsActifs();
    }

    public List<VueRemboursementDTO> getRemboursements() {
        return rapportRepository.getRemboursements();
    }
}
