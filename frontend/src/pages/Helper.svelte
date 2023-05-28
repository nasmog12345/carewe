<script>
    import { onMount } from "svelte";
    import axios from "axios";
    import { jwt_token} from "../store";
 
  
    const api_root = window.location.origin;
  
    let helpers = [];
 let helper = {
      id: null,
      name: null,
      address: null,
      email: null,
      skills: [],
      bio: null,
helperState: null,

      //helperState: null,
    };
  
    function getHelpers() {
  axios
    .get(api_root + "/api/helper", {
      headers: {
        "Authorization": "Bearer " + $jwt_token,
      },
    })
    .then(function (response) {
      helpers = response.data;
    })
    .catch(function (error) {
      alert("Could not get helpers");
      console.log(error);
    });
}

    


    function createHelper() {
      axios
        .post(api_root + "/api/helper", helper, {
          headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + $jwt_token,
          },
        })
        .then(function (response) {
          alert("Helper created");
          getHelpers();
        })
        .catch(function (error) {
          alert("Could not create Helper");
          console.log(error);
        });
    }
  
    onMount(() => {
      getHelpers();
    });
    
  </script>
  
  <h1 class="mt-3">Create Helper</h1>
  <form class="mb-5">
    <div class="row mb-3">
      <div class="col">
        <label class="form-label" for="name">Name</label>
        <input
          bind:value={helper.name}
          class="form-control"
          id="name"
          type="text"
        />
      </div>
    </div>
    <div class="row mb-3">
      <div class="col">
        <label class="form-label" for="name">Adresse</label>
        <input
          bind:value={helper.address}
          class="form-control"
          id="address"
          type="text"
        />
      </div>
    </div>
    <div class="row mb-3">
      <div class="col">
        <label class="form-label" for="email">E-Mail</label>
        <input
          bind:value={helper.email}
          class="form-control"
          id="email"
          type="email"
        />
      </div>
    </div>
    <div class="row mb-3">
      <div class="col">
        <label class="form-label" for="type">Skills</label>
        <select
          bind:value={helper.skills}
          class="form-control"
          id="skills"
          
          multiple
        >
          <option value="Handwerker">Handwerker</option>
          <option value="KV">KV</option>
          <option value="IT">IT</option>
          <option value="Elektriker">Elektriker</option>
        </select>
      </div>
    </div>
    <div class="row mb-3">
      <div class="col">
        <label class="form-label" for="bio">Bio</label>
        <textarea
          bind:value={helper.bio}
          class="form-control"
          id="bio"
          rows="5"
        ></textarea>
      </div>
    </div>
    
    <div class="row mb-3">
      <div class="col">
          <label class="form-label" for="type">HelperState</label>
          <select
              bind:value={helper.helperState}
              class="form-select"
              id="type"
              type="text"
          >
              <option value="ASSIGNED">ASSIGNED</option>
              <option value="UNALLOCATED">UNALLOCATED</option>
              <option value="DONE">DONE</option>
              </select>
      </div>
  
    <button type="button" class="btn btn-primary" on:click={createHelper}>
      Submit
    </button>
  </form>
  
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
  