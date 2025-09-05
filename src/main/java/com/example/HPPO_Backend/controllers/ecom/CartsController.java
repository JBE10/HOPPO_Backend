package com.example.HPPO_Backend.controllers.ecom;
 
import com.example.HPPO_Backend.entity.Cart;
import com.example.HPPO_Backend.entity.User;
import com.example.HPPO_Backend.entity.dto.CartRequest;
import com.example.HPPO_Backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.net.URI;
import java.util.Optional;
 
@RestController
@RequestMapping("carts")
public class CartsController {
    @Autowired
    private CartService cartService;
 
    @GetMapping
    public ResponseEntity<Page<Cart>> getCarts(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(cartService.getCarts(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(cartService.getCarts(PageRequest.of(page, size)));
    }
 

 
    @PostMapping
    public ResponseEntity<Object> createCart(@RequestBody CartRequest cartRequest) {
        Cart result = this.cartService.createCart(cartRequest);
        return ResponseEntity.created(URI.create("/carts/" + result.getId())).body(result);
    }

    @GetMapping("/my-cart")
    public ResponseEntity<Cart> getMyCart(@AuthenticationPrincipal User user) {
        Optional<Cart> result = this.cartService.getCartByUserId(user.getId());
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping({"/{cartId}"})
    public ResponseEntity<Cart> getCartById(@PathVariable Long cartId) {

        Optional<Cart> result = this.cartService.getCartById(cartId);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }
}