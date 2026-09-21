# STEP Java Assignments

This repository contains Java program solutions for the STEP semester assignments:

- **Week 1**: Core Java concepts, arrays, loops, and conditional logic.
- **Week 2**: String manipulation, formatting, and data structures.
- **Week 3**: Object-Oriented Programming (OOP) concepts, inheritance, encapsulation, and static vs. instance design.
- **Week 4**: Constructors, Java Keywords, domain modeling, and validations.
- **Week 5**: Access modifiers, package structures, and class hierarchies.
- **Week 6**: Multilevel and hierarchical inheritance, polymorphic dispatch, and downcasting.
- **Week 7**: Abstract classes vs. capability interfaces, compile-time & runtime polymorphism, method overloading, upcasting, and safe downcasting.

---

## Week 7 Overview & Problem Summary

Week 7 focuses on core Object-Oriented Programming (OOP) concepts: abstract classes, pure capability contracts via interfaces, multilevel and hierarchical inheritance, compile-time and runtime polymorphism, method overloading, encapsulation, upcasting, and safe downcasting with `instanceof`.

### Category A Practice Problems (`week7/practice/`)

1. **Problem 1: Checkout Payment Handler**
   - `PaymentMethod`: Abstract class with a shared static counter generating `transactionId` (`TXN-1001`, `TXN-1002`, etc.), abstract `processPayment(double amount)`, and overloaded `processPayment(double amount, String note)`. Includes `printConfirmation(PaymentMethod, double)` demonstrating runtime polymorphic dispatch and upcasting.
   - `CreditCardPayment`: Concrete subclass implementing payment processing for credit cards.
   - `CashPayment`: Concrete subclass implementing cash payments.

2. **Problem 2: Home Safety Alert Network**
   - `Alertable`: Pure capability interface declaring `sendAlert(String message)`. Includes static `broadcastAll(Alertable[], String)` and `getZoneIfMotionSensor(Alertable)` for safe downcasting via `instanceof`.
   - `SecuritySensor`: Concrete base class encapsulating `zoneName`.
   - `MotionSensor`: Extends `SecuritySensor` and implements `Alertable`.
   - `DualZoneMotionSensor`: Extends `MotionSensor` (multilevel hierarchy) and reuses `super.sendAlert(message)`.
   - `SmokeDetector`: Implements `Alertable` directly with no relationship to `SecuritySensor`.

3. **Problem 3: Quarterly Bonus Calculator**
   - `StaffMember`: Abstract class with constructor chaining via `this(baseSalary, 0.10)`, encapsulated `baseSalary` rejecting negative values, abstract `calculateBonus()`, and static `getAuditIfApplicable(StaffMember)`.
   - `Auditable`: Separate compliance interface with `auditRecord()`.
   - `TeamLead`: Extends `StaffMember` and implements `Auditable`.

4. **Problem 4: Universal Media Launcher**
   - `Playable`: Interface declaring `play()`, overloaded `play(int fromSecond)`, `pause()`, and `launchAll(Playable[])`. Documents design justification: `AudioFile` IS-A `MediaFile` vs. `Podcast` CAN-DO `Playable`.
   - `MediaFile`: Abstract class with shared static `fileId` generator (`MF-1001`, etc.) and abstract `getFormatInfo()`.
   - `AudioFile`: Extends `MediaFile` and implements `Playable`.
   - `Podcast`: Implements `Playable` independently.

5. **Problem 5: Community Library Checkout System**
   - `LibraryItem`: Abstract class with static `itemId` generator (`LIB-1001`, etc.), abstract `getLoanPeriodDays()`, `processCheckouts(LibraryItem[])`, and `reserveIfSupported(Object)`.
   - `Renewable` & `Reservable`: Distinct capability interfaces.
   - `Textbook`: Extends `LibraryItem` and implements both `Renewable` and `Reservable`.
   - `Magazine`: Extends `LibraryItem` and implements only `Renewable`.
   - `DigitalPass`: Implements only `Renewable` with no relationship to `LibraryItem`.

---

### Category A Assignment Problems (`week7/assignment/`)

1. **Problem 1: Basic Drawing Canvas**
   - `Shape`: Abstract class with static `shapeId` (`SHAPE-1001`, etc.), abstract `calculateArea()`, and overloaded concrete `scale(double factor)` and `scale(double xFactor, double yFactor)` methods.
   - `CircleShape`: Extends `Shape` with area calculation.
   - `SquareShape`: Extends `Shape` with side scaling and area calculation.

2. **Problem 2: One-Click Data Export**
   - `Exportable`: Capability interface managing a shared static export counter, `getTotalExports()`, and `exportAll(Exportable[])`.
   - `ReportGenerator` & `UserProfile`: Unrelated classes implementing `Exportable` directly.

3. **Problem 3: Fleet Maintenance Tracker**
   - `ServiceableVehicle`: Abstract class with encapsulated `mileage` rejecting negative values in `addMileage(double km)`, abstract `performMaintenance()`, and `getInsuranceIfApplicable(ServiceableVehicle)`.
   - `Insurable`: Interface declaring `getInsuranceInfo()`.
   - `Forklift`: Extends `ServiceableVehicle` and implements `Insurable`.
   - `HeavyDutyForklift`: Multilevel hierarchy extending `Forklift` and overriding `performMaintenance()` via `super.performMaintenance()`.

4. **Problem 4: Arena Battle Simulator**
   - `Attackable`: Interface with `attack()` and overloaded `attack(String weaponName)`.
   - `Defendable`: Interface with `defend()` and static `resolveDefense(Defendable[])`.
   - `GameCharacter`: Abstract base class with static `characterId` generator (`GC-1001`, etc.) and abstract `getSpecialMove()`.
   - `Warrior`: Implements both `Attackable` and `Defendable`.
   - `Trap`: Non-character arena element implementing only `Defendable`.

5. **Problem 5: Connected Home Control Panel**
   - `HomeDevice`: Abstract base class with static `serialNumber` generator (`HD-1001`, etc.), abstract `activate()`, and `getConsumptionIfTrackable(HomeDevice)`.
   - `RemoteControllable` & `EnergyTrackable`: Independent capability interfaces.
   - `WashingMachine`: Extends `HomeDevice` and implements both `RemoteControllable` and `EnergyTrackable`.
   - `Refrigerator`: Extends `HomeDevice` and implements only `EnergyTrackable`.
   - `MobileApp`: Standalone application implementing only `RemoteControllable`.

---

## Directory Layout (Week 7)

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