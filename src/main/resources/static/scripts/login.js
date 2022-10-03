var { createApp } = Vue;

createApp({
  data() {
    return {
      email: "",
      password: "",
    };
  },

  created() {},
  mounted() {},
  methods: {
    login(event) {
      event.preventDefault();
      axios
        .post("/api/login", `email=${this.email}&password=${this.password}`, {
          headers: { "content-type": "application/x-www-form-urlencoded" },
        })
        .then((response) => {
          if (this.email.includes("@admin")) {
            Swal.fire({
              title: "Bienvenido a tu HomeBanking BANCARIBE!!",
              imageUrl: "/img/bienvenido.png",
              imageWidth: 300,
              imageHeight: 300,
              imageAlt: "Custom image",
              showCancelButton: true,
              confirmButtonColor: "#20DBC2",
              confirmButtonText: "OK",
            }).then((result) => {
              if (result.isConfirmed) {
                window.location.href = "/manager/admin.html";
              }
            });
          } else {
            Swal.fire({
              title: "Bienvenido a tu HomeBanking BANCARIBE!!",
              imageUrl: "/img/bienvenido.png",
              imageWidth: 300,
              imageHeight: 300,
              imageAlt: "Custom image",
              showCancelButton: true,
              confirmButtonColor: "#20DBC2",
              confirmButtonText: "OK",
            }).then((result) => {
              if (result.isConfirmed) {
                window.location.href = "/web/accounts.html";
              }
            });
          }
        })
        .catch((error) => {
          if (this.email == "" && this.password == "") {
          Swal.fire({
            icon: "error",
            title: "Upss..",
            text: "Debes ingresar Usuario y Contraseña",
            confirmButtonColor: "#20DBC2",
          });
        }

        if (this.email == "") {
          Swal.fire({
            icon: "error",
            title: "Upss..",
            text: "Debes ingresar el Usuario",
            confirmButtonColor: "#20DBC2",
          });
        }

         if (this.password == "") {
           Swal.fire({
             icon: "error",
             title: "Upss..",
             text: "Debes ingresar la Contraseña",
             confirmButtonColor: "#20DBC2",
           });
         }

        if (!this.email.includes("@", ".com")) {
          Swal.fire({
            icon: "error",
            title: "Upss..",
            text: "Debes ingresar un email valido",
            confirmButtonColor: "#20DBC2",
          });
        }


        });
    },
  },
  computed: {},
}).mount("#app");
