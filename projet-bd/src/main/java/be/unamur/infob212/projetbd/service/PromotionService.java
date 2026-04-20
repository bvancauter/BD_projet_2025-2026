package be.unamur.infob212.projetbd.service;

import be.unamur.infob212.projetbd.dto.Promotion.PromotionFull;
import be.unamur.infob212.projetbd.dto.Promotion.PromotionList;
import be.unamur.infob212.projetbd.dto.Promotion.PromotionSave;
import be.unamur.infob212.projetbd.model.Promotion;
import be.unamur.infob212.projetbd.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository promotionRepository;

    public List<PromotionList> getAll() {
        return promotionRepository.findAll()
                .stream()
                .map(this::toListDto)
                .toList();
    }

    public Optional<PromotionFull> get(Integer id) {
        return promotionRepository.findById(id)
                .map(this::toFullDto);
    }

    public PromotionFull create(PromotionSave dto) {
        Promotion promotion = toEntity(dto);
        Promotion saved = promotionRepository.save(promotion);
        return toFullDto(saved);
    }

    public PromotionFull update(Integer id, PromotionFull dto) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PROMO_NOT_FOUND"));

        promotion.setNom(dto.getNom());
        promotion.setDescription(dto.getDescription());
        promotion.setPourcentage(dto.getPourcentage());
        promotion.setDateDebut(dto.getDateDebut());
        promotion.setDateFin(dto.getDateFin());

        return toFullDto(promotionRepository.save(promotion));
    }

    public void delete(Integer id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PROMO_NOT_FOUND"));
        promotionRepository.delete(promotion);
    }

    private PromotionList toListDto(Promotion p) {
        PromotionList dto = new PromotionList();
        dto.setId(p.getId());
        dto.setNom(p.getNom());
        dto.setPourcentage(p.getPourcentage());
        return dto;
    }

    private PromotionFull toFullDto(Promotion p) {
        PromotionFull dto = new PromotionFull();
        dto.setId(p.getId());
        dto.setNom(p.getNom());
        dto.setDescription(p.getDescription());
        dto.setPourcentage(p.getPourcentage());
        dto.setDateDebut(p.getDateDebut());
        dto.setDateFin(p.getDateFin());
        return dto;
    }

    private Promotion toEntity(PromotionSave dto) {
        Promotion p = new Promotion();
        p.setNom(dto.getNom());
        p.setDescription(dto.getDescription());
        p.setPourcentage(dto.getPourcentage());
        p.setDateDebut(dto.getDateDebut());
        p.setDateFin(dto.getDateFin());
        return p;
    }
}
