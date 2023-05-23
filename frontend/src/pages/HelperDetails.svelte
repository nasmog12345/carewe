<script>
  import axios from 'axios';
  import Rating from './Rating.svelte';
  import { jwt_token} from "../store";

  const api_root = 'http://localhost:8080';
  export let params = {};

  let helpers = [];

  function getHelpers() {
    var config = {
      method: 'get',
      maxBodyLength: Infinity,
      url: api_root +`/api/helper/${params.id}`,
      headers: {"Authorization": "Bearer " + $jwt_token,},
    };
  
    axios(config)
      .then(function (response) {
        helpers = response.data;

      })
      .catch(function (error) {
        console.log(error);
      });
  }

  
  
  // call the getHelper function to populate the helper object
 
  getHelpers();

</script>


<div class="card profile-container">
  <div class="card-header profile-header">
    <h2>{helpers.name}</h2>
    <p>{helpers.email}</p>
  </div>
  <div class="card-body profile-body">
    <p><strong>ID:</strong> {helpers.id}</p>
    <p><strong>Address:</strong> {helpers.address}</p>
    <p><strong>Skills:</strong> {helpers.skills}</p>
    <p><strong>Bio:</strong> {helpers.bio}</p>
  </div>
  
  <div class="rating-wrapper">
    <Rating/>
  </div>
</div>


<!-- profil ung so erstellen:
auf der rechten seite infos wie name usw (abgegerenzt ) durch einen dunklen balken. 
neben dem balken weisse fläche mit bio (text) und rating. 

-->


