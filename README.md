# STEP Java Assignments

This repository contains Java program solutions for the STEP semester assignments:

- **Week 1**: Core Java concepts, arrays, loops, and conditional logic.
- **Week 2**: String manipulation, formatting, and data structures.
- **Week 3**: Object-Oriented Programming (OOP) concepts, inheritance, encapsulation, and static vs. instance design.
- **Week 4**: Constructors, Java Keywords, domain modeling, and validations.
- **Week 5**: Access modifiers, package structures, and class hierarchies.
- **Week 6**: Multilevel and hierarchical inheritance, polymorphic dispatch, and downcasting.
- **Week 7**: Abstract classes vs. capability interfaces, compile-time & runtime polymorphism, method overloading, upcasting, and safe downcasting.
- **Week 8**: Advanced OOP domain modeling, state management, extensible scoring and pricing strategies, capability composition, and event notifications.

---

## Week 7 Overview & Problem Summary

Week 7 focuses on core Object-Oriented Programming (OOP) concepts: abstract classes, pure capability contracts via interfaces, multilevel and hierarchical inheritance, compile-time and runtime polymorphism, method overloading, encapsulation, upcasting, and safe downcasting with `instanceof`.

### Category A Practice Problems (`week7/practice/`)

1. **Problem 1: Checkout Payment Handler** (`CheckoutPaymentHandler.java`)
   - `PaymentMethod`: Abstract class with a shared static counter generating `transactionId` (`TXN-1001`, `TXN-1002`, etc.), abstract `processPayment(double amount)`, and overloaded `processPayment(double amount, String note)`. Includes `printConfirmation(PaymentMethod, double)` demonstrating runtime polymorphic dispatch and upcasting.
   - `CreditCardPayment`: Concrete subclass implementing payment processing for credit cards.
   - `CashPayment`: Concrete subclass implementing cash payments.

2. **Problem 2: Home Safety Alert Network** (`HomeSafetyAlertNetwork.java`)
   - `Alertable`: Pure capability interface declaring `sendAlert(String message)`. Includes static `broadcastAll(Alertable[], String)` and `getZoneIfMotionSensor(Alertable)` for safe downcasting via `instanceof`.
   - `SecuritySensor`: Concrete base class encapsulating `zoneName`.
   - `MotionSensor`: Extends `SecuritySensor` and implements `Alertable`.
   - `DualZoneMotionSensor`: Extends `MotionSensor` (multilevel hierarchy) and reuses `super.sendAlert(message)`.
   - `SmokeDetector`: Implements `Alertable` directly with no relationship to `SecuritySensor`.

3. **Problem 3: Quarterly Bonus Calculator** (`QuarterlyBonusCalculator.java`)
   - `StaffMember`: Abstract class with constructor chaining via `this(baseSalary, 0.10)`, encapsulated `baseSalary` rejecting negative values, abstract `calculateBonus()`, and static `getAuditIfApplicable(StaffMember)`.
   - `Auditable`: Separate compliance interface with `auditRecord()`.
   - `TeamLead`: Extends `StaffMember` and implements `Auditable`.

4. **Problem 4: Universal Media Launcher** (`UniversalMediaLauncher.java`)
   - `Playable`: Interface declaring `play()`, overloaded `play(int fromSecond)`, `pause()`, and `launchAll(Playable[])`. Documents design justification: `AudioFile` IS-A `MediaFile` vs. `Podcast` CAN-DO `Playable`.
   - `MediaFile`: Abstract class with shared static `fileId` generator (`MF-1001`, etc.) and abstract `getFormatInfo()`.
   - `AudioFile`: Extends `MediaFile` and implements `Playable`.
   - `Podcast`: Implements `Playable` independently.

5. **Problem 5: Community Library Checkout System** (`CommunityLibraryCheckoutSystem.java`)
   - `LibraryItem`: Abstract class with static `itemId` generator (`LIB-1001`, etc.), abstract `getLoanPeriodDays()`, `processCheckouts(LibraryItem[])`, and `reserveIfSupported(Object)`.
   - `Renewable` & `Reservable`: Distinct capability interfaces.
   - `Textbook`: Extends `LibraryItem` and implements both `Renewable` and `Reservable`.
   - `Magazine`: Extends `LibraryItem` and implements only `Renewable`.
   - `DigitalPass`: Implements only `Renewable` with no relationship to `LibraryItem`.

### Category A Assignment Problems (`week7/assignment/`)

1. **Problem 1: Basic Drawing Canvas** (`BasicDrawingCanvas.java`)
   - `Shape`: Abstract class with static `shapeId` (`SHAPE-1001`, etc.), abstract `calculateArea()`, and overloaded concrete `scale(double factor)` and `scale(double xFactor, double yFactor)` methods.
   - `CircleShape`: Extends `Shape` with area calculation.
   - `SquareShape`: Extends `Shape` with side scaling and area calculation.

2. **Problem 2: One-Click Data Export** (`OneClickDataExport.java`)
   - `Exportable`: Capability interface managing a shared static export counter, `getTotalExports()`, and `exportAll(Exportable[])`.
   - `ReportGenerator` & `UserProfile`: Unrelated classes implementing `Exportable` directly.

3. **Problem 3: Fleet Maintenance Tracker** (`FleetMaintenanceTracker.java`)
   - `ServiceableVehicle`: Abstract class with encapsulated `mileage` rejecting negative values in `addMileage(double km)`, abstract `performMaintenance()`, and `getInsuranceIfApplicable(ServiceableVehicle)`.
   - `Insurable`: Interface declaring `getInsuranceInfo()`.
   - `Forklift`: Extends `ServiceableVehicle` and implements `Insurable`.
   - `HeavyDutyForklift`: Multilevel hierarchy extending `Forklift` and overriding `performMaintenance()` via `super.performMaintenance()`.

4. **Problem 4: Arena Battle Simulator** (`ArenaBattleSimulator.java`)
   - `Attackable`: Interface with `attack()` and overloaded `attack(String weaponName)`.
   - `Defendable`: Interface with `defend()` and static `resolveDefense(Defendable[])`.
   - `GameCharacter`: Abstract base class with static `characterId` generator (`GC-1001`, etc.) and abstract `getSpecialMove()`.
   - `Warrior`: Implements both `Attackable` and `Defendable`.
   - `Trap`: Non-character arena element implementing only `Defendable`.

5. **Problem 5: Connected Home Control Panel** (`ConnectedHomeControlPanel.java`)
   - `HomeDevice`: Abstract base class with static `serialNumber` generator (`HD-1001`, etc.), abstract `activate()`, and `getConsumptionIfTrackable(HomeDevice)`.
   - `RemoteControllable` & `EnergyTrackable`: Independent capability interfaces.
   - `WashingMachine`: Extends `HomeDevice` and implements both `RemoteControllable` and `EnergyTrackable`.
   - `Refrigerator`: Extends `HomeDevice` and implements only `EnergyTrackable`.
   - `MobileApp`: Standalone application implementing only `RemoteControllable`.

### Directory Layout (Week 7)

```
week7/
├── practice/
│   ├── CheckoutPaymentHandler.java
│   ├── CommunityLibraryCheckoutSystem.java
│   ├── HomeSafetyAlertNetwork.java
│   ├── QuarterlyBonusCalculator.java
│   └── UniversalMediaLauncher.java
└── assignment/
    ├── ArenaBattleSimulator.java
    ├── BasicDrawingCanvas.java
    ├── ConnectedHomeControlPanel.java
    ├── FleetMaintenanceTracker.java
    └── OneClickDataExport.java
```

---

## Week 8 Overview & Problem Summary

Week 8 focuses on advanced Object-Oriented domain modeling, state management, extensible scoring and pricing strategies, capability composition (composition over inheritance), and event communication.

### Category A Practice Problems (`week8/practice/`)

1. **Question 1: Online Examination System** (`OnlineExaminationSystem.java`)
   - Manages question sets polymorphically via `Question` abstraction (`MultipleChoiceQuestion`, `TrueFalseQuestion`).
   - `Attempt` enforces answer immutability once submitted and calculates score across all questions.

2. **Question 2: Vehicle Rental System with Dynamic Pricing** (`VehicleRentalSystem.java`)
   - Extensible vehicle hierarchy (`LuxuryCar`, `StandardCar`, `SUVCar`) calculating rental charges polymorphically based on category and duration.
   - `RentalService` coordinates vehicle availability tracking, rental creation, and return workflows.

3. **Question 3: Hotel Booking and Cancellation System** (`HotelBookingSystem.java`)
   - Prevents overlapping reservations for the same room using date interval overlap detection.
   - Pricing calculated by room category (`STANDARD`, `DELUXE`, `SUITE`) and duration.
   - Enforces cancellation deadlines before check-in.

4. **Question 4: Employee Leave Request Management** (`EmployeeLeaveManagement.java`)
   - Supports different employee policies (`FullTimeEmployee`, `PartTimeEmployee`, `ContractEmployee`).
   - Enforces irreversible review transitions (`PENDING -> APPROVED / REJECTED`), rejecting attempts to revert to pending once decided.

5. **Question 5: Food Order and Flexible Payment System** (`FoodOrderSystem.java`)
   - Validates that orders cannot be placed with empty carts.
   - Polymorphic payment processing via `IPaymentMethod` (`CreditCardPayment`, `DigitalWalletPayment`).
   - Customer notification callback upon key order lifecycle events (`Order Placed`, `Paid`, `Pending Payment`).

### Category A Assignment Problems (`week8/assignment/`)

1. **Question 1: The Code Sprint Judging Desk** (`CodeSprintJudgingDesk.java`)
   - Hackathon lifecycle management with team size constraints (2 to 4 members) and strict one-team-per-student validation.
   - Extensible scoring strategies: Innovation track (weighted: 50% idea, 30% execution, 20% presentation) and Open track (simple average).
   - Locks scores once results are published, rejecting subsequent rescore attempts.

2. **Question 2: The SwiftShip Parcel Tracker** (`SwiftShipParcelTracker.java`)
   - Enforces strict unidirectional status transitions: `BOOKED -> PICKED_UP -> IN_TRANSIT -> OUT_FOR_DELIVERY -> DELIVERED`.
   - Cancellation permitted only while `BOOKED`.
   - Extensible shipping charge strategies: Standard (₹40 + ₹10/kg), Express (₹80 + ₹15/kg), Fragile (Standard + ₹50 fee).
   - Observer pattern for multi-channel notifications (`SmsChannel`, `EmailChannel`).

3. **Question 3: The Smart Lab Control Panel** (`SmartLabControlPanel.java`)
   - Smart devices composed of runtime capabilities (`PowerCapability`, `BrightnessCapability`, `TemperatureCapability`) avoiding class explosion.
   - Dynamic runtime capability addition to existing devices.
   - Scene execution (`Lecture Mode`) targeting specific capabilities across devices with range validation.

4. **Question 4: The Elective Seat Rush** (`ElectiveSeatRush.java`)
   - Fixed elective capacity with FIFO waitlist queue.
   - Validates semester credit limits before seat availability (Regular: 24, Honors: 28, Exchange: 20).
   - Drop-and-promote consistency: dropping an elective automatically promotes and enrolls the first eligible student on the waitlist.

5. **Question 5: The Campus Canteen Smart Card** (`CampusCanteenSmartCard.java`)
   - Prepaid smart card maintaining invariant: `balance == sum(transactions)`.
   - Minimum top-up ₹100, maximum balance ₹5,000.
   - Extensible pricing plans: Day Scholar (full price), Hosteller (10% off), Staff (20% off).
   - One-time refund constraint returning the exact charged amount, active/blocked state handling, and mini-statement generation.

### Directory Layout (Week 8)

```
week8/
├── practice/
│   ├── EmployeeLeaveManagement.java
│   ├── FoodOrderSystem.java
│   ├── HotelBookingSystem.java
│   ├── OnlineExaminationSystem.java
│   └── VehicleRentalSystem.java
└── assignment/
    ├── CampusCanteenSmartCard.java
    ├── CodeSprintJudgingDesk.java
    ├── ElectiveSeatRush.java
    ├── SmartLabControlPanel.java
    └── SwiftShipParcelTracker.java
```
