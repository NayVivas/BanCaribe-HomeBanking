const { createApp } = Vue;

createApp({
  data() {
    return {
      clients: [],
      accounts: [],
      loans: [],
      accountsLength: [],
      numberAccounts1: "",
      numberAccounts2: "",
      originAccounts: "",
      destinyAccounts: "",
      amount: "",
      description: "",
      originNumber: "",
      destinyNumber: "",
      numberAccounts: "",
      accountsCurrent: "",
    };
  },

  created() {
    this.loadData();
    this.myAccounts();
  },
  mounted() {},
  methods: {
    loadData() {
      axios.get("/api/clients/current").then((respuesta) => {
        this.clients = respuesta.data;
      });
    },

    clientXML() {
    axios.get('http://localhost:8080/api/clients/current',{headers:{'accept':'application/xml'}}).then(response =>

 console.log(response.data))
    },

    logout() {
      axios
        .post(`/api/logout`)
        .then((response) => (window.location.href = "/index.html"));
    },

    myAccounts() {
      axios.get("/api/clients/current/accounts").then((respuesta) => {
        this.accountsCurrent = respuesta.data;
        console.log(this.accountsCurrent);

        this.numberAccounts = this.accountsCurrent.filter(
          (number) => number.balance > 0
        );
        console.log(this.numberAccounts);
      });
    },

    tranfers(event) {
      event.preventDefault();
      axios
        .post(
          "/api/transactions",
          "amount=" +
            this.amount +
            "&description=" +
            this.description +
            "&originNumber=" +
            this.originAccounts +
            "&destinyNumber=" +
            this.destinyAccounts,
          {
            headers: { "content-type": "application/x-www-form-urlencoded" },
          }
        )
        .then((respuesta) => {
          Swal.fire({
            title: "Esta seguro de realizar la transferencia?",
            imageUrl: "/img/preguntaUno.png",
            imageWidth: 200,
            imageHeight: 300,
            imageAlt: "Custom image",
            showCancelButton: true,
            confirmButtonColor: "#20DBC2",
            cancelButtonColor: "#d33",
            confirmButtonText: "Confirmar!",
          }).then((result) => {
            if (result.isConfirmed) {
              Swal.fire("Transferencia exitosa!!");
            }
          });
        })
        .catch((error) => {
          this.error = error.response.data;
          console.log(
            this.amount,
            this.description,
            this.originAccounts,
            this.destinyAccounts
          );
          console.log(this.error);
          if (this.error == "Parametro vacio") {
            Swal.fire({
              icon: "error",
              title: "Upss..",
              text: "Debes rellenar todos los datos!",
              confirmButtonColor: "#20DBC2",
            });
          }
          if (this.error == "la cuenta de origen no existe") {
            Swal.fire({
              icon: "error",
              title: "Upss..",
              text: "La cuenta de origen no existe!",
              confirmButtonColor: "#20DBC2",
            });
          }
          if (this.error == "Las cuentas de son iguales") {
            Swal.fire({
              icon: "error",
              title: "Upss..",
              text: "La cuenta de origen y destino son iguales!",
              confirmButtonColor: "#20DBC2",
            });
          }
          if (
            this.error ==
            "la cuenta de origen no pertenece al cliente autenticado"
          ) {
            Swal.fire({
              icon: "error",
              title: "Upss..",
              text: "Esta cuenta no te pertenece!",
              confirmButtonColor: "#20DBC2",
            });
          }
          if (this.error == "la cuenta de destino no existe") {
            Swal.fire({
              icon: "error",
              title: "Upss..",
              text: "Esta cuenta de destino no existe!",
              confirmButtonColor: "#20DBC2",
            });
          }
          if (this.error == "cliente no tiene fondos") {
            Swal.fire({
              icon: "error",
              title: "Upss..",
              text: "No tienes fondos suficientes!",
              confirmButtonColor: "#20DBC2",
            });
          }
        });
    },

    
    originAccount() {
      document.querySelector(".titulos").style.display = "none";
      document.querySelector(".container-one").style.display = "none";
      document.querySelector(".container-two").style.display = "block";
      document.querySelector(".container-two").style.marginTop = "10%";
      document.querySelector(".container-two").style.marginLeft = "10%";
    },
    destinyAccount() {
      document.querySelector(".titulos").style.display = "none";
      document.querySelector(".container-one").style.display = "none";
      document.querySelector(".container-three").style.display = "block";
      document.querySelector(".container-three").style.marginTop = "10%";
      document.querySelector(".container-three").style.marginLeft = "10%";
    },
  },
  computed: {},
}).mount("#app");
