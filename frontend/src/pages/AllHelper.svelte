<script>
    import { onMount } from "svelte";
    import axios from "axios";
    import { jwt_token} from "../store";
 
  
    const api_root = "http://localhost:8080";
  
    let helpers = [];
 
  
    function getHelpers() {
    var config = {
        method: 'get',
        url: api_root + "/api/helper",
        headers: {
        "Authorization": "Bearer " + $jwt_token,
      },
    };

    axios(config)
    .then(function (response) {
        helpers = response.data;
    })
    .catch(function (error) {
        alert("Could not get helpers");
        console.log(error);
    });
}

function navigateCreateHelper() {
        location.href = '#/helper';
    }
    function handleNavigation() {
    location.href = "#/helperhome";
  }

  
    onMount(() => {
      getHelpers();
    });
    
  </script>

  <button type="button" class="btn" on:click={navigateCreateHelper}>Create Helper</button>
  <button type="button" class="btn" on:click={handleNavigation}>zurück</button>
  
  <h1>All helpers</h1>
  <table class="table">
    <thead>
      <tr>
        <th scope="col">Name</th>
        <th scope="col">Adresse</th>
        <th scope="col">E-Mail</th>
        <th scope="col">Skills</th>
        <th scope="col">Bio</th>
        <th scope="col">Helperstate</th>
      </tr>
    </thead>
    <tbody>
      {#each helpers as helper}
      <tr>
        <td>{helper.name}</td>
        <td>{helper.address}</td>
        <td>{helper.email}</td>
        <td>{helper.skills}</td>
        <td>{helper.bio}</td>
        <td>{helper.helperState}</td>
      </tr>
      {/each}
    </tbody>
  </table>
  