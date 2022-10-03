var { createApp } = Vue;

createApp({
  data() {
    return {
      firstValueName: "",
      lastValueName: "",
      emailValue: "",
      password: "",
      mensajeRegister: "",
    };
  },

  created() {},
  mounted() {},
  methods: {
    addClient() {
      if (
        this.firstValueName &&
        this.lastValueName &&
        this.emailValue.includes("@")
      ) {
        let client = {
          firstName: this.firstValueName,
          lastName: this.lastValueName,
          emailClient: this.emailValue,
          password: this.password,
        };
        this.postClient(client);
      }
    },

    postClient() {
      axios
        .post(
          "/api/clients",
          "firstName=" +
            this.firstValueName +
            "&lastName=" +
            this.lastValueName +
            "&email=" +
            this.emailValue +
            "&password=" +
            this.password,
          { headers: { "content-type": "application/x-www-form-urlencoded" } }
        )
        .then((response) => {
          Swal.fire({
            title: "Felicidades te has registrado con exito!!",
            imageUrl: "/img/bienvenido2.png",
            imageWidth: 200,
            imageHeight: 300,
            imageAlt: "Custom image",
            showCancelButton: true,
            confirmButtonColor: "#20DBC2",
            confirmButtonText: "OK",
          }).then((result) => {
            if (result.isConfirmed) {
              window.location.href = "login.html";
            }
          });
        })
        .catch((error) => {
          this.error = error.response.data;
          console.log(this.error);
            Swal.fire({
              icon: "error",
              title: "Upss..",
              text: this.error,
              confirmButtonColor: "#20DBC2",
            });
        });
    },
  },
  computed: {},
}).mount("#app1");
