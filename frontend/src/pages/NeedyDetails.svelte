<script>
    import axios from "axios";
    import { jwt_token } from "../store";
    import Rating from './Rating.svelte';
    
    const api_root = "http://localhost:8080";
    export let params = {};

    
    let needies = [];

    async function getNeedyById(){
       var config = {
        method: 'get',
        url: api_root + `/api/needy/${params.id}`,
        headers: {"Authorization": "Bearer " + $jwt_token,}
      };
  
      axios(config)
      .then(function (response) {
        needies = response.data;

      })
      .catch(function (error) {
        console.log(error);
      });
  }
    

    // Call the function immediately
    getNeedyById(); 
</script>


<div class="card profile-container">
  <div class="card-header profile-header">
    <h2>{needies.name}</h2>
    <p>{needies.email}</p>
  </div>
  <div class="card-body profile-body">
    <p><strong>ID:</strong> {needies.id}</p>
    <p><strong>Address:</strong> {needies.address}</p>
    <p><strong>Number:</strong> {needies.number}</p>
    <p><strong>Needs:</strong> {needies.needs}</p>
    <p><strong>Notes:</strong> {needies.notes}</p>
    <p><strong>State:</strong> {needies.needyState}</p>
  </div>

  <div class="rating-wrapper">
    <Rating/>
  </div>
</div>



<style>
  
</style>

