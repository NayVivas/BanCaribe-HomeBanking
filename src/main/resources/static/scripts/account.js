const { createApp } = Vue;

createApp({
  data() {
    return {
      clients: [],
      accounts: [],
      accountSelect: "",
      transacciones: [],
      nameAccount: [],
      account: "",
      accountNumber: "",
      balanceNumber: "",
      creationDate: "",
      filtroAccounts: "",
      transaccionesId: "",
    };
  },

  created() {
    this.loadData();
   
  },
  mounted() {},
  methods: {
    loadData() {
      let idURL = new URL(window.location).searchParams.get("id");
      console.log(idURL);

      axios.get(`/api/accounts/${idURL}`).then((respuesta) => {
        this.account = respuesta.data;
        console.log(this.account)

        this.transacciones = this.account.transactions
        console.log(this.transacciones)
      
      });
      
      axios.get("/api/clients/current").then((respuesta) => {
        this.clients = respuesta.data;
        this.accounts = this.clients.account;
        this.accounts = this.clients.account.filter((account) => account.id == idURL)
      });
    },

    smalldate(creationDate) {
      creationDate = new Date(creationDate).toLocaleDateString();
      console.log(creationDate);   
      return creationDate  
    },

    logout() {
      axios
        .post(`/api/logout`)
        .then((response) => (window.location.href = "/login.html"));
    },
  },
  computed: {},
}).mount("#app");


/*    let idAccount = location.search.split("?id=").join("");
        console.log(idAccount);
        this.accountSelect = this.accounts.filter(account => account.id == idAccount);
        console.log(this.accountSelect)
        this.accountNumber = this.accountSelect[0].numberAccount;
        console.log(this.accountNumber)
        this.balanceNumber = this.accountSelect[0].balance;
        console.log(this.balanceNumber)
        this.nameAccount = this.accountSelect[0].numberAccount;
        console.log(this.nameAccount);
        this.transacciones = this.accountSelect[0].transactions
        console.log(this.transacciones); */