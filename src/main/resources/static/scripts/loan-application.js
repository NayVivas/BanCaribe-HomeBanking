const { createApp } = Vue;

createApp({
  data() {
    return {
      loanApply: "",
      loanPayments: "",
      loanAmount: "",
      loanAccount: "",
      clients: [],
      loans: [],
      paymentsLoans: [],
      payment: [],
      maxAmounts: [],
      destinyAccount: [],
      numberAccounts: [],
      error: "",
      loanId: 0,
    };
  },

  created() {
    this.loadData();
    this.paymentsLoan();
    this.myAccounts();
  },
  mounted() {},

  methods: {
    loadData() {
      axios.get("/api/clients/current").then((respuesta) => {
        this.clients = respuesta.data;
      });
    },
    logout() {
      axios
        .post(`/api/logout`)
        .then((response) => (window.location.href = "/index.html"));
    },

    paymentsLoan() {
      axios.get("/api/loans").then((response) => {
        this.loans = response.data;
        console.log(this.loans);
        this.selectLoan = this.loans.filter(
          (loan) => loan.name == this.loanApply);
        console.log(this.selectLoan);
        this.payment = this.selectLoan[0].payments;
        console.log(this.payment);
        this.maxAmounts = this.selectLoan[0].maxAmount;
        console.log(this.maxAmount);
      });
    },

    myAccounts() {
      axios.get("/api/clients/current/accounts").then((respuesta) => {
        this.destinyAccount = respuesta.data;
        console.log(this.destinyAccount);

        this.numberAccounts = this.destinyAccount.map(
          (number) => number.numberAccount
        );
        console.log(this.numberAccounts);
      });
    },

    createLoan(event) {
      event.preventDefault();
       this.loanId = this.loans.filter(
         (loan) => loan.name == this.loanApply
       );
          console.log(
            this.loanId[0].id,
            this.loanAmount,
            this.loanPayments,
            this.loanAccount
          );
      axios
        .post("/api/loans", {
          "id": this.loanId[0].id,
          "amount": this.loanAmount,
          "payments": this.loanPayments,
          "destinyNumberAccount": this.loanAccount,
        })
        .then((response) => {
          Swal.fire({
            title: "Confirmas la solicitud del prestamo?",
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
              window.location.href = "/web/accounts.html";
            }
          });
        })
        .catch((error) => {
          this.error = error.response.data;
          console.log(this.error);
          if (this.error == "enter all the requested data") {
            Swal.fire({
              icon: "error",
              title: "Upss..",
              text: "Debes rellenar todos los datos!",
              confirmButtonColor: "#20DBC2",
            });
          }
          if (this.error == "the loan does not exist") {
            Swal.fire({
              icon: "error",
              title: "Upss..",
              text: "El prestamo que desea solicitar no existe!",
              confirmButtonColor: "#20DBC2",
            });
          }
          if (
            this.error == "The amount to request exceeds the amount of the loan"
          ) {
            Swal.fire({
              icon: "error",
              title: "Upss..",
              text: "El monto que deseas solicitar excede el limite!",
              confirmButtonColor: "#20DBC2",
            });
          }
          if (
            this.error == "It is not possible to request that amount of quotas"
          ) {
            Swal.fire({
              icon: "error",
              title: "Upss..",
              text: "El prestamo seleccionado no esa cantidad de cuotas!",
              confirmButtonColor: "#20DBC2",
            });
          }
          if (this.error == "Destination account does not exist") {
            Swal.fire({
              icon: "error",
              title: "Upss..",
              text: "Esta cuenta de destino no existe!",
              confirmButtonColor: "#20DBC2",
            });
          }
          if (
            this.error == "The account does not belong to the authenticated client"
          ) {
            Swal.fire({
              icon: "error",
              title: "Upss..",
              text: "La cuenta que deseas enviar los fondos, no es tuya!",
              confirmButtonColor: "#20DBC2",
            });
          }
        });
    },
  },
  computed: {},
}).mount("#app");
