<script>
  import axios from "axios";
  import { user, jwt_token } from "../store";
  import GetRating from "./GetRating.svelte";
  const api_root = "http://localhost:8080";

  
  const urlParam = window.location.href.split('/')[5];

  let rating = {
      titel: "",
      name: "",
        zahl: "",
      rezession: "",
      helperId: urlParam
  };
let clicked = false;


  function createRating() {
      var config = {
          method: "post",
          url: api_root + "/api/rating",
          headers: {
              "Content-Type": "application/json",
              "Authorization": "Bearer " + $jwt_token,
          },
          data: rating
      };

      axios(config)
          .then(function (response) {
            alert("Danke für die Bewertung")
              console.log(response.data);
          })
          .catch(function (error) {
              alert("Could not create rating");
              console.log(error);
          });
  }

  function handleSubmit(event) {
    event.preventDefault();
    createRating();
  }

  function toggle(){
    clicked = !clicked;
  }
</script>

<!-- nur admins können rating verfassen-->
{#if $user.user_roles && $user.user_roles.includes("admin")}
<button on:click={toggle}>Create Needy</button>
{#if clicked}
<form on:submit={handleSubmit}>
  <label for="titel">Titel</label>
  <input type="text" id="titel" bind:value={rating.titel} />

  <label for="name">Name</label>
  <input type="text" id="name" bind:value={rating.name} />

  <label for="zahl">Zahl</label>
  <input type="number" id="zahl" bind:value={rating.zahl} min="1" max="5" />
  

  <label for="rezession">Rezession</label>
  <textarea id="rezession" bind:value={rating.rezession} />

  <button type="submit">Submit</button>
</form>
{/if}

<GetRating/>
{/if}
