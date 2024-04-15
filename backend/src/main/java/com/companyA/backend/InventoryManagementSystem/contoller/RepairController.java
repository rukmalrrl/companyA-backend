package com.companyA.backend.InventoryManagementSystem.contoller;

import com.companyA.backend.InventoryManagementSystem.model.Inventory;
import com.companyA.backend.InventoryManagementSystem.model.Repair;
import com.companyA.backend.InventoryManagementSystem.model.SendForRepair;
import com.companyA.backend.InventoryManagementSystem.model.Stocks;
import com.companyA.backend.InventoryManagementSystem.service.RepairService;
import com.companyA.backend.InventoryManagementSystem.service.SendForRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repair")
public class RepairController {

    @Autowired
    private RepairService repairService;

    @Autowired
    private SendForRepairService sendForRepairService;

    /////////////////////////////////////////////////////////////////
    //Give ids of the items with stateOfProduct = "DAMAGED"
    @GetMapping("/damaged-products")
    public ResponseEntity<List<String>> getIdsOfDamagedProducts() {
        List<String> damagedProductIds = repairService.getIdsOfDamagedProducts();
        return ResponseEntity.ok(damagedProductIds);
    }

    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //StateOfProduct will be updated as "UNDER_REPAIR"("Inventory" collection)
    //Send the details of relevant items to the "Repair" collection

    @GetMapping("/send-for-repair")
    public ResponseEntity<String> sendItemsForRepair(@RequestBody List<String> itemIds) {
        repairService.sendItemsForRepair(itemIds);
        return ResponseEntity.ok("Items sent for repairs successfully.");
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    //Can directly add an item to repair collection even if that relevant item is not exist in the inventory collection
    //This might be not useful.Please check! //Wadk nh wge nm ain krla danna
    @PostMapping("/send-for-repair-collection")
    public ResponseEntity<String> sendForRepairs(@RequestBody SendForRepair sendForRepair) {
        String sup =  repairService.sendForRepairs(sendForRepair);
        return ResponseEntity.status(HttpStatus.OK).body(sup);
    }

    //When you give an repairIds list,this will give you the item details that is "UNDER_REPAIR"
    //Even if a single id in the list isn't exist in the "Repair" collection, "wrong product id" message will appear
    @GetMapping("/checkRepairApi")
    public ResponseEntity<Object> getRepairDetails(@RequestBody List<String> ids) {
        try {
            List<SendForRepair> repairs = repairService.repairDetails(ids);
            return ResponseEntity.ok(repairs);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("wrong product id");
        }
    }

    //Can directly delete an item to repair collection
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRepairById(@PathVariable String id) {
        try {
            repairService.deleteRepairById(id);
            return ResponseEntity.ok("Item deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////
    @GetMapping("repaired-items")
    public ResponseEntity<String> updateRepairedItems(@RequestBody List<String> itemIds) {
        repairService.updateRepairedItems(itemIds);
        return ResponseEntity.ok("Items has been repaired");
    }
}
