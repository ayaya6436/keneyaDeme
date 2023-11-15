package keneyaDeme.keneyaDeme.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;


import keneyaDeme.keneyaDeme.model.Zones;
import keneyaDeme.keneyaDeme.repository.ZoneRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ZoneService {
    private final ZoneRepository zoneRepository;

    public Zones createZone(Zones zone) {
        if (zoneRepository.findByNom(zone.getNom()) == null) {
            return zoneRepository.save(zone);
        }else
            {
                throw new IllegalArgumentException("La zone " + zone.getNom() + " existe deja");
            }
        
    }

    public long getNumberOfZones() {
        try {
            return zoneRepository.count(); 
        } catch (Exception ex) {
            throw new RuntimeException("Une erreur s'est produite lors de la récupération du nombre de zone", ex);
        }
    }
    
    public List<Zones> readAll() {
    try {
        List<Zones> zonesList = zoneRepository.findAll();
        if (zonesList.isEmpty()) {
            throw new NoSuchElementException("La liste des zones est vide");
        }
        return zonesList;
    } catch (NoSuchElementException ex) {
        throw ex; // Permettre à l'ApplicationControllerAdvice de gérer cette exception
    } catch (Exception ex) {
        throw new RuntimeException("Une erreur s'est produite lors de la lecture de la liste des zones", ex);
    }
}

public Zones readById(Long id) {
    return zoneRepository.findById(id).orElseThrow(() -> new RuntimeException("Zone non trouvé !"));
       
    }

      public Zones updateZone(Long id, Zones zone) {
        Optional<Zones> existingZone = zoneRepository.findById(id);
        if (existingZone.isPresent()) {
            Zones updatedZone = existingZone.get();
            updatedZone.setNom(zone.getNom());
            updatedZone.setLongitude(zone.getLongitude());
            updatedZone.setLatitude(zone.getLatitude());
            return zoneRepository.save(updatedZone);
        } else {
            
             throw new NoSuchElementException("Zones non trouvé avec l'ID : " + id);
        }
    }

        
 public String supprimer(Long id) {
       Optional <Zones> zone = zoneRepository.findById(id);
        if (zone.isPresent()) {
            zoneRepository.deleteById(id);
            return "Zone supprimé avec succès.";
        } else {
          
            return "Zone non trouvé.";
        }
    }
    
}
