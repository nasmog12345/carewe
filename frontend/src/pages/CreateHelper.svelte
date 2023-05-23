<script>
    import { onMount } from "svelte";
    import axios from "axios";
    import { jwt_token} from "../store";
 
  
    const api_root = "http://localhost:8080";
  
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
         
        })
        .catch(function (error) {
          alert("Could not create Helper");
          console.log(error);
        });
    }
  

    function navigateCreateHelper() {
        location.href = '#/helperhome';
    }

    function navigateAllHelpers() {
        location.href = '#/allhelper';
    }

    function handleNavigation() {
    location.href = "#/helperhome";
  }


  </script>
  
  
<button type="button" class="btn" on:click={navigateAllHelpers}>See all Helpers</button>
<button type="button" class="btn btn-primary" on:click={navigateCreateHelper}>
    zurück
  </button>
  
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
  