package in.furqanali.MoneyManager.controller;

import in.furqanali.MoneyManager.dto.AuthDto;
import in.furqanali.MoneyManager.dto.ProfileDto;
import in.furqanali.MoneyManager.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@RestController

@RequiredArgsConstructor


public class ProfileController {
    @GetMapping("/hello")
    public String Hello(){

        return "HELLO";
    }

    private final ProfileService profileService;
@PostMapping("/register")
    public ResponseEntity<ProfileDto> registerProfile(@RequestBody ProfileDto profileDto){
    ProfileDto registeredProfile=profileService.registerProfile(profileDto);
        System.out.println("Controller Hit");
        log.info("controller hit");
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredProfile);
    }
    @GetMapping("/activate")
    public ResponseEntity<String>activateProfile(@RequestParam String token){
    boolean isActivated= profileService.activateProfile(token);
    if(isActivated){
        return ResponseEntity.ok("Profile activated successfully");
    }
    else{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Activation token not found or already used");
    }
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody AuthDto authDto){
    try{
        if(!profileService.isAccountActive(authDto.getEmail())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message","Account is not active. Please Activate account first"));
        }
      Map<String,Object> response=profileService.authenticateAndGenerateToken(authDto);
        return ResponseEntity.ok(response);
    }

    catch (Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",e.getMessage()));
    }
    }



}
