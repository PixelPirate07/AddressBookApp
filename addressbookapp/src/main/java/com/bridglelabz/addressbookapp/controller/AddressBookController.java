package com.bridglelabz.addressbookapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/addressbook")
public class AddressBookController {

    private List<String> dummyData = new ArrayList<>();

    // GET: Fetch all entries
    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllEntries() {
        return new ResponseEntity<>(dummyData, HttpStatus.OK);
    }

    // GET: Fetch by ID
    @GetMapping("/{id}")
    public ResponseEntity<String> getEntryById(@PathVariable int id) {
        if (id >= 0 && id < dummyData.size()) {
            return new ResponseEntity<>(dummyData.get(id), HttpStatus.OK);
        }
        return new ResponseEntity<>("Entry not found", HttpStatus.NOT_FOUND);
    }

    // POST: Create new entry
    @PostMapping("/create")
    public ResponseEntity<String> createEntry(@RequestBody String entry) {
        dummyData.add(entry);
        return new ResponseEntity<>("Entry added successfully", HttpStatus.CREATED);
    }

    // PUT: Update existing entry
    @PutMapping("/{id}")
    public ResponseEntity<String> updateEntry(@PathVariable int id, @RequestBody String newEntry) {
        if (id >= 0 && id < dummyData.size()) {
            dummyData.set(id, newEntry);
            return new ResponseEntity<>("Entry updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Entry not found", HttpStatus.NOT_FOUND);
    }

    // DELETE: Remove entry
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEntry(@PathVariable int id) {
        if (id >= 0 && id < dummyData.size()) {
            dummyData.remove(id);
            return new ResponseEntity<>("Entry deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Entry not found", HttpStatus.NOT_FOUND);
    }
}