# Sujet FullStack : Optimisation de Stock de yahourts

### How to launch it

For back-end part, in stockmanagement folder, run in terminal :
```
./mvnw spring-boot:run
```

For front-end part, in stockfront folder, run in terminal, depending your node package manager :
```
npm run dev -- -o
```


## Updates from 2025-03-17
### Frontend
- Now, you can use OptionAPI or CompositionAPI code formats of Vue.js.<br>
To switch for one to another, just comment / uncomment the wanted one on app.vue file.
- As reseponse data has been corrected from the backend, the chart and table display correct data.

### Backend
- Correction on logic part of the app to correctly update stock when there is no more yoghurts to consume.
