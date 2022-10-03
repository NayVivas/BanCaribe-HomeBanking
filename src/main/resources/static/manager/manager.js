const { createApp } = Vue;

createApp({
  data() {
    return {
      listaClientes: [],
      firstValueName: "",
      lastValueName: "",
      emailValue: "",
      json: [],
      indice: [],
    };
  },

  created() {
    this.loadData();
  },
  mounted() {},
  methods: {
   loadData() {
      axios.get("/rest/clients")
      .then(respuesta => {
        this.json = respuesta;
        this.listaClientes = respuesta.data._embedded.clients;
        console.log(this.listaClientes)
      }) 
    },
   addClient(e) {
    e.preventDefault();
      if(this.firstValueName && this.lastValueName && this.emailValue.includes("@")) {
          let client = {
          firstName: this.firstValueName,
          lastName: this.lastValueName,
          emailClient: this.emailValue,
        };
        this.postClient(client);
      } 
    },
    postClient(client) {
      axios.post("/rest/clients", client)
      .then(respuesta => {
        this.loadData()
      })
    },
    removeElement(idClientes) {
      this.idClientes = idClientes._links.client.href
      axios.delete(this.idClientes)
      .then(remove => {
        this.loadData()
    })
  } 
  },
  computed: {
   
  },
}).mount("#app");