<script>
  import axios from "axios";
  import { user, jwt_token } from "../store";
  const api_root = "http://localhost:8080";
  
  
  const urlParam = window.location.href.split('/')[5];
  console.log(urlParam)

  export let ratings = [];
  let rating = {
      titel: "",
      name: "",
      zahl: "",
      rezession: "",
      helperId: "",
  };
  let average = 0;
    
  function getRating() {
      var config = {
          method: "get",
          url: api_root + "/api/rating/helper/" + urlParam,
          headers: {
              "Authorization": "Bearer " + $jwt_token,
          },
       
      };

      axios(config)
          .then(function (response) {
              ratings = response.data;
              durchschnitt();
          })
          .catch(function (error) {
              alert("Could not get rating");
              console.log(error);
          });
  }
 

  function durchschnitt(){
      let total = 0;
      for (let rating of ratings) {
          total += rating.zahl;
      }
      average = total / ratings.length;
  }

  getRating();
 
</script>

<p>Durchschnittliche Bewertung: {average}</p>

<div class="wrapper">
<div class="output-container">
  {#each ratings as rating}
    <div class="rating-output">
      <p>{rating.titel}</p>
      <p>{rating.name}</p>
      <p>{rating.zahl}</p>
      <p>{rating.rezession}</p>
      <p>{rating.helperId}</p>
    </div>
  {/each}
</div>
</div>

