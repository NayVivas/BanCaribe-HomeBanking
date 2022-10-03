let { createApp } = Vue;

createApp({
  data() {
    return {
      clients: [],
      card: [],
      typeColor: [],
      type: [],
      cardsCredit: [],
      cardsDebit: [],
      cardsActive: [],
      selectCard: "",
      yearActual: "",
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

    getSelectCard() {
      axios.get("/api/clients/current").then((respuesta) => {
        this.clients = respuesta.data;
        this.card = this.clients.cards;
        console.log(this.card);

        this.selectCards = this.card.filter(
          (card) => card.number == this.selectCard
        );

        this.cardID = this.selectCards[0].id;
      });
    },

    deleteCard(id) {
      axios
        .patch("/api/clients/current/cards/delete/" + id)
        .then((response) => {
          return window.location.reload();
        });
    }, 

    smalldate(creationDate) {
      creationDate = new Date(creationDate).toLocaleDateString();
      console.log(creationDate);
      return creationDate;
    },

    logout() {
      axios
        .post(`/api/logout`)
        .then((response) => (window.location.href = "/login.html"));
    },
  },
  computed: {},
}).mount("#app");

/* .catch(error => {
  swel.fire((
    icon: "error",
    tittle: "Oops..",
    text: error.response.data,
    confirmButtonColor: "#028484",
  ))
}) */
