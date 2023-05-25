<script>
  import { onMount } from 'svelte';
  import axios from 'axios';
  import HelperCard from './HelperCard.svelte';
  import NeedyMatch from './NeedyMatch.svelte';
  import { jwt_token} from "../store";

  const api_root = "http://localhost:8080";
  let filteredItems;
let displaySearch = false;
  let searchTerm = '';
  let helpers = [];
  let helper = {
      id: null,
      name: null,
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


  function filterItems() {
  if (searchTerm) {
    const filtered = helpers.filter(helper => {
      return helper.skills.some(skill => skill.toLowerCase().includes(searchTerm.trim().toLowerCase()));
    });
    if (filtered.length === 0) {
      alert('No matching helper found!');
    }
    return filtered;
  } else {
    return [];
  }
}

  function handleSearch() {
    displaySearch = true;
    filteredItems = filterItems();
  }

  $: filteredItems = helpers;
  


  $: getHelpers();

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


</script>


<input type="text" bind:value={searchTerm} placeholder="Search">
<button on:click={handleSearch}>Search</button>

{#if displaySearch}
  
{#each filteredItems as item}

<HelperCard helper={item}/>

<NeedyMatch needyId={item.id}/>

{/each}
{/if}

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




