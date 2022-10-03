const { createApp } = Vue;

createApp({
  data() {
    return {
      clients: [],
        loanName: "",
        maxAmount: "",
        percentage: "",
        listPayments: "",
        loans: "",
    };
  },

  created() {
    this.loadData();
    this.loanMostrar();
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

    loanMostrar() {
      axios.get("/api/loans")
      .then((response) => {
        this.loans = response.data;
        console.log(this.loans)
      })
    },

    loanCreate(event) {
      event.preventDefault();
      axios
        .post(
          "/api/loans/create",
          `loanName=${this.loanName}&maxAmount=${this.maxAmount}&payments=${this.listPayments}&percentage=${this.percentage}`
        )
        .then((response) => {
          Swal.fire({
            title: "Confirmas la creación de un nuevo prestamo?",
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
              window.location.href = "/manager/loanAdmin.html";
            }
          });
        })
        .catch((error) => {
          this.error = error.response.data;
          console.log(this.error);
          Swal.fire({
            icon: "error",
            title: "Upss..",
            text: error.response.data,
            confirmButtonColor: "#20DBC2",
          });
        });
    }
  },
  computed: {},
}).mount("#app");



 /* listPayments(number) {
      if(this.newLoan.listPayments.includes(number)) {
        indexofNumber = this.newLoan.listPayments.indexOf(number)
        this.newLoan.listPayments.splice(indexofNumber, 1)
      }
      else {
        this.newLoan.listPayments.push(number)
      }
      console.log(this.newLoan.listPayments)
    },
 */
