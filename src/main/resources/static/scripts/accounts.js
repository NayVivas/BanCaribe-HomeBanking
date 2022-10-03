const { createApp } = Vue;

createApp({
  data() {
    return {
      clients: [],
      accounts: [],
      loans: [],
      accountsLength: [],
      accountsActive: [],
      accountID: "",
      selectAccounts: "",
      selectAccount: "",
      value: "",
    };
  },

  created() {
    this.loadData();
    this.yearActual = new Date(Date.now()).getFullYear().toString().slice(2, 4);
    console.log(this.yearActual)
  },
  mounted() {},
  methods: {
    loadData() {
      axios.get("/api/clients/current").then((respuesta) => {
        this.clients = respuesta.data;
        console.log(this.clients);
        this.accounts = this.clients.account;
        console.log(this.accounts);

        this.loans = this.clients.loans;
        console.log(this.loans);

        this.accountsActive = this.accounts.filter(
          (account) => account.accountActive == true
        );
        console.log(this.accountsActive);

        this.card = this.clients.cards;
        console.log(this.card);

        this.cardsCredit = this.card.filter((card) => card.type == "CREDIT");
        console.log(this.cardsCredit);

        this.cardsDebit = this.card.filter((card) => card.type == "DEBIT");
        console.log(this.cardsDebit);

        this.cardsActive = this.card.filter((card) => card.cardActive == true);
        console.log(this.cardsActive);
      });
    },

    smalldate(creationDate) {
      creationDate = new Date(creationDate).toLocaleDateString();
      console.log(creationDate);
      return creationDate;
    },

    getSelectAccount() {
      axios.get("/api/clients/current").then((respuesta) => {
        this.clients = respuesta.data;
        
        this.accounts = this.clients.account;
        console.log(this.accounts);

        this.selectAccounts = this.accounts.filter(
          (account) => account.numberAccount == this.selectAccount
        );
        console.log(this.selectAccounts);
        this.accountID = this.selectAccounts[0].id;
        console.log(this.accountID);
      });
    },

    deleteAccount(id) {
      axios
        .patch("/api/clients/current/accounts/delete/" + id)
        .then((response) => {
          return window.location.reload();
        })
        .catch((error) => {
          this.error = error.response.data;
          console.log(this.error)
          })
    },

    logout() {
      axios
        .post(`/api/logout`)
        .then((response) => (window.location.href = "/login.html"));
    },

    createdAccountAhorroCorriente() {
      Swal.fire({
        title: "Deseas crear una cuenta de AHORRO o CORRIENTE?",
        input: "select",
        inputOptions: {
          Ahorro: "AHORRO",
          Corriente: "CORRIENTE",
        },
        inputPlaceholder: "Seleccione un tipo de cuenta",
        showCancelButton: true,
        confirmButtonColor: "#20DBC2",
        cancelButtonColor: "#d33",
        confirmButtonText: "Confirmar!",
        cancelButtonText: "Cancelar",
        inputValidator: (value) => {
          value = value.toUpperCase(); console.log(value)
            if (value == "AHORRO") {
              console.log(value)
              Swal.fire({
                title: "Confirmas la creación de una nueva cuenta de AHORRO?",
                imageUrl: "/img/preguntaDos.png",
                imageWidth: 200,
                imageHeight: 300,
                imageAlt: "Custom image",
                showCancelButton: true,
                confirmButtonColor: "#20DBC2",
                cancelButtonColor: "#d33",
                confirmButtonText: "Confirmar!",
                cancelButtonText: "Cancelar",
              }).then((result) => {
                console.log("true")
                if (result.isConfirmed) {
                   axios.post(
                     `/api/clients/current/accounts?accountType=${value}`,
                     {
                       headers: {
                         "content-type": "application/x-www-form-urlencoded",
                       },
                     }
                   )
                   .then((response) => {
                    location.reload();
                    console.log("lo que sea")
                   })
                   .catch((error) => {
                    console.log(error.response.data)
                   })
                }
              });
            } else if (value == "CORRIENTE") {
              console.log(value);
              Swal.fire({
                title:
                  "Confirmas la creación de una nueva cuenta de CORRIENTE?",
                imageUrl: "/img/preguntaDos.png",
                imageWidth: 200,
                imageHeight: 300,
                imageAlt: "Custom image",
                showCancelButton: true,
                confirmButtonColor: "#20DBC2",
                cancelButtonColor: "#d33",
                confirmButtonText: "Confirmar!",
                cancelButtonText: "Cancelar",
              })
                .then((result) => {
                  if (result.isConfirmed) {
                    axios.post(
                      `/api/clients/current/accounts?accountType=${value}`,
                      {
                        headers: {
                          "content-type": "application/x-www-form-urlencoded",
                        },
                      }
                    );
                  }
                })
                .then((result) => {
                  location.reload();
                });
            }
        },
      });
    },
    
    createAccount() {
      axios
        .post("/api/clients/current/accounts", {
          headers: { "content-type": "application/x-www-form-urlencoded" },
        })
        .then((respuesta) => {
          Swal.fire({
            title: "Confirmas la creación de una nueva cuenta?",
            imageUrl: "/img/preguntaDos.png",
            imageWidth: 200,
            imageHeight: 300,
            imageAlt: "Custom image",
            showCancelButton: true,
            confirmButtonColor: "#20DBC2",
            cancelButtonColor: "#d33",
            confirmButtonText: "Confirmar!",
            cancelButtonText: "Cancelar",
          }).then((result) => {
            if (result.isConfirmed) {
              location.reload();
            }
          });
        });
    },
    /*  mostrarTabla(id) {
        axios.get("/api/clients/1").then((respuesta) => {
        this.clients = respuesta.data;
        this.accounts = this.clients.account;
        console.log(this.accounts);

       this.accountFilter = this.accounts.filter(account => account.id == id);
        console.log(this.accountFilter) 
        document.querySelector(".card-container").style.width = "70vw";
        document.querySelector(".card-container").style.marginTop = "10%";
        document.querySelector("section").style.visibility = "visible";
        document.querySelector("section").style.marginTop = "-30%";
        document.querySelector("section").style.width = "50vw";
        document.querySelector("section").style.marginLeft = "45%";
        document.querySelector(".buttonCuentas").style.textDecoration = "none";
        document.querySelector(".buttonCuentas").style.color = "#004983";
        document.querySelector(".prestamo").style.visibility = "hidden";
      });
    } */
  },
  computed: {},
}).mount("#app");
