let { createApp } = Vue;

createApp({
  data() {
    return {
      card: "",
      clients: "",
      checkType: "",
      checkColor: "",
      allCards: "",
    };
  },

  created() {
    this.loadData();
  },
  mounted() {},
  methods: {
    loadData() {
      axios.get("/api/clients/current").then((respuesta) => {
        this.clients = respuesta.data;
        this.card = this.clients.cards;
        console.log(this.card);

        this.cardsCredit = this.card.filter((card) => card.type == "CREDIT");
        console.log(this.cardsCredit);

        this.cardsDebit = this.card.filter((card) => card.type == "DEBIT");
        console.log(this.cardsDebit);
      });
    },

    createCard() {
      axios
        .post(
          "/api/clients/current/cards",
          "cardType=" + this.checkType + "&cardColor=" + this.checkColor,
          { headers: { "content-type": "application/x-www-form-urlencoded" } }
        )
        .then((response) => {
          Swal.fire({
            title: "Confirma la nueva solicitud de tarjeta?",
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
              window.location.href = "card.html";
            }
          });
        })
        .catch((error) => {
          this.error = error.response.data;
          console.log(this.error);
          Swal.fire({
            icon: "error",
            title: "Upss..",
            text: "Lo sentimos, ya posees una tarjeta de este tipo!",
            confirmButtonColor: "#20DBC2",
          });
        });
    },

    logout() {
      axios
        .post(`/api/logout`)
        .then((response) => (window.location.href = "/login.html"));
    },

    /* debitCard () {
      document.querySelector(".container-card-credito").style.display = "none";
    },
    
    creditCard () {
      document.querySelector(".container-card-debito").style.display = "none";
      document.querySelector(".container-card-credito").style.marginTop = "30%";
    },

    allCard () {
      document.querySelector(".container-card-debito").style.display = "block";
      document.querySelector(".container-card-credito").style.display = "block";
      document.querySelector("#td2").style.display = "block";
      document.querySelector("#td1").style.display = "block";
      document.querySelector("#td3").style.display = "block";
    },

    goldCard () {
      document.querySelector("#td2").style.display = "none";
      document.querySelector("#td1").style.display = "none";
    },

    silverCard () {
      document.querySelector("#td3").style.display = "none";
      document.querySelector("#td1").style.display = "none";
    },

    titaniumCard () {
      document.querySelector("#td3").style.display = "none";
      document.querySelector("#td2").style.display = "none";
    } */
  },
  computed: {},
}).mount("#app2");
