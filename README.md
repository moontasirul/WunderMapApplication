# WunderMapApplication

# Uses Tools & Technology

    # Kotlin
    # Coroutines
    # MVVM
    # Retrofit
    # Hilt
    # Live Data
    # Data Binding

1. Describe possible performance optimizations for your Code. I am trying to use reactive features,
   > Some UI module can be more optimize as well more scope of using data binding. Dialog view features can be more optimize


2. Which things could be done better, than you’ve done it? After third reservation api call and
   > result will success then user can not know what to do or what will happened. So I have implement a bottom sheet view which will show after reservation successfully done and back to the previous screen. 1 > Reservation data will store locally, 2 > if user click on the dismiss button on bottom sheet then data data will remove.