
Vladimir_Vysokomornyi@epam.com
==============================

## HW2

### [-]
- Booking operation just save the tickets via its dao. Where the booking logic?
- Aspects have very strange/wrong pointcuts. It must targets interfaces.
- DiscountAspect doesn't count how many times each discount was given total.
- LuckyWinnerAspect implemented not OK. Firstly there is no "luckyEvents" in code. This aspect must somehow modify the tickets price.


### [+]
- Moved to Java-based Spring config
- Working tests for aspects

### [comments]
1. Implemented main logic which was needed for HW1. Still no That's good. But you got time penalty. I will updated result for first task.
2. Aspect implementation no perfect, has issues, not full

### [result]
80