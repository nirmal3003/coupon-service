# Monk Commerce – Coupons Management API

##  Objective

Build a RESTful API to create, manage, and apply different types of discount coupons (cart-wise, product-wise, and Buy-X-Get-Y) for an e-commerce platform.
The system is designed to be extensible so new coupon types can be added easily.

---

#  Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA
* MySQL
* Maven
* Lombok

---

#  Architecture

The system uses **Strategy Pattern** for coupon logic.

Each coupon type has its own strategy:

* CartWiseCouponStrategy
* ProductWiseCouponStrategy
* BxGyCouponStrategy

This allows adding new coupon types without modifying existing code.

---

#  Coupon Types Implemented

## 1️ Cart-wise Coupon

Discount applied on entire cart if cart total exceeds threshold.

Example:
10% off on carts above ₹100

Condition:
cart_total > threshold

---

## 2️ Product-wise Coupon

Discount applied only to specific product in cart.

Example:
20% off on Product 1

Condition:
product exists in cart

---

## 3️ Buy-X-Get-Y (BxGy)

Buy certain products → get some products free.

Example:
Buy 2 from [A,B] → Get 1 from [C] free

Supports:

* Multiple buy products
* Multiple get products
* Repetition limit

---

#  API Endpoints

## Coupon CRUD

POST /coupons
GET /coupons
GET /coupons/{id}
PUT /coupons/{id}
DELETE /coupons/{id}

---

## Coupon Application

POST /coupons/applicable-coupons
→ Returns all applicable coupons for cart

POST /coupons/apply-coupon/{id}
→ Applies coupon to cart

---

#  Cart Structure

```json
{
  "cart": {
    "items": [
      { "productId": 1, "quantity": 2, "price": 100 },
      { "productId": 2, "quantity": 1, "price": 50 }
    ]
  }
}
```

---

#  Discount Calculation Rules

## Cart-wise

discount = cart_total x percentage / 100

## Product-wise

discount = product_price x quantity x percentage

## BxGy

1. Count buy items
2. Count get items
3. Determine applicable repetitions
4. Discount = price of free items

---

#  Implemented Cases

* Cart threshold validation
* Product presence validation
* Multiple buy products
* Multiple get products
* Repetition limit
* Partial applicability
* Multiple coupons evaluation
* Coupon CRUD
* Apply coupon to cart
* Applicable coupons listing

---

#  Database Design

Entity: Coupon

Fields:

* id
* type
* threshold
* discount
* productId
* buyProductIds
* getProductIds
* buyQuantity
* getQuantity
* repetitionLimit
* expiryDate

Element collections store product lists.

---

#  Sample Coupon Payloads

## Cart-wise

```json
{
  "type": "CART_WISE",
  "threshold": 100,
  "discount": 10
}
```

## Product-wise

```json
{
  "type": "PRODUCT_WISE",
  "productId": 1,
  "discount": 20
}
```

## BxGy

```json
{
  "type": "BXGY",
  "buyProductIds": [1,2],
  "getProductIds": [3],
  "buyQuantity": 2,
  "getQuantity": 1,
  "repetitionLimit": 3
}
```

---


#  Conclusion

The system fulfills the Monk Commerce requirements:

* Coupon CRUD
* Applicable coupons calculation
* Apply coupon
* Multiple coupon types
* Extensible design

Strategy pattern ensures easy addition of new coupon types in future.
