package keneyaDeme.keneyaDeme.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import keneyaDeme.keneyaDeme.model.Zones;

import keneyaDeme.keneyaDeme.service.ZoneService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")

@RequestMapping("/keneya")

@Valid
@Validated
public class ZoneController {
     private final ZoneService zoneService;

    @PostMapping("/zone")
    @Operation(summary = "Creer une nouvelle zone")
    public Zones create(@Valid @RequestBody Zones zone) {
        return zoneService.createZone(zone);
    }


    @GetMapping("/zones/count") 
    @Operation(summary = "Afficher le nombre de zone")
    public long getNumberOfZones() {
        return zoneService.getNumberOfZones();
    }
    @GetMapping("/zones")
    @Operation(summary = "Afficher la liste des zones")
    public List<Zones> read() {
        return zoneService.readAll();
    }

    @GetMapping("/zone/{id}")
    @Operation(summary = "Récupère une zone grâce à son ID ")
    public Zones read(@Valid @PathVariable Long id) {
        return zoneService.readById(id);
    }

    @PutMapping("/zone/{id}")
    @Operation(summary = "Récupère une zone grâce à son ID  et le modifie")
    public Zones update(@Valid @PathVariable Long id, @RequestBody Zones zone) {
        return zoneService.updateZone(id, zone);
    }

    @DeleteMapping("/zone/{id}")
    @Operation(summary = "Supprimer une zone par son ID")

    public String delete(@Valid @PathVariable Long id) {
        return zoneService.supprimer(id);
    }
}
