<script>
    import axios from "axios";
    import { user, jwt_token, myHelperId } from "../store";
    import { querystring } from "svelte-spa-router";
  
    // Update this to point to the URL of your Spring Boot backend
    const api_root = "http://localhost:8080";

    let currentPage;
let nrOfPages = 0;
let defaultPageSize = 10;
let needyState;
let message = '';
  
    let needies = [];

    $: {
let searchParams = new URLSearchParams($querystring);
if (searchParams.has("page")) {
currentPage = searchParams.get("page");
} else {
currentPage = "1";
}
getNeedies();
}
  
    function getNeedies() {
      let query = "?pageSize=" + defaultPageSize + "&pageNumber=" + currentPage;
      var config = {
        method: "get",
        url: api_root + "/api/needy" + query,
        headers: { "Authorization": "Bearer " + $jwt_token },
      };
  
      axios(config)
        .then(function (response) {
          needies = response.data.content;
          nrOfPages = response.data.totalPages;
        })
        .catch(function (error) {
          alert("Could not get needies");
          console.log(error);
        });
    }
  
    function assignToNeedy(needyId) {
      var config = {
        method: "put",
        url: api_root + "/api/service/me/assignneedy?needyId=" + needyId,
        headers: { "Authorization": "Bearer " + $jwt_token },
      };
      axios(config)
        .then(function (response) {
          getNeedies(); // or whatever function you use to refresh your needy list
          message = "Die Nummer des Needy ist nun für die Kontaktaufnahme verfügbar"; // Set the message text
        })
        .catch(function (error) {
          alert("Could not assign needy to me");
          console.log(error);
        });
    }
  
    function completeNeedy(needyId) {
      var config = {
        method: "put",
        url: api_root + "/api/service/me/completeneedy?needyId=" + needyId,
        headers: { "Authorization": "Bearer " + $jwt_token },
      };
  
      axios(config)
        .then(function (response) {
          console.log(JSON.stringify(response.data));
          getNeedies();
        })
        .catch(function (error) {
          alert("Could not mark as completed");
          console.log(error);
        });
    }
  
    function handleNavigation() {
      location.href = "#/needyhome";
    }
  
    function navigateCreateNeedy() {
      location.href = "#/needy";
    }
  
   
  </script>
<img src="/bilder/6.png" alt="img" class="angepasstes-bild">

{#if message}
<div class="alert alert-success">
  {message}
</div>
{/if}

  
  <button type="button" class="btn" on:click={navigateCreateNeedy}>Create Needy</button>
  <button type="button" class="btn" on:click={handleNavigation}>zurück</button>

  <div class="row my-3">
    <div class="col-auto">
      <label for="" class="col-form-label">Needy State: </label>
    </div>
    <div class="col-3">
      <select bind:value={needyState} class="form-select" id="needyState" type="text">
        <option value="ALL"></option>
        <option value="UNALLOCATED">UNALLOCATED</option>
        <option value="ASSIGNED">ASSIGNED</option>
        <option value="DONE">DONE</option>
      </select>
    </div>
    <div class="col-3">
      <a class="btn btn-primary" href={"#/allneedy?page=1&needyState=" + needyState} role="button">Apply</a>
    </div>
  </div>
  
  
  <h1>All Needies</h1>
  <table class="table">
    <thead>
      <tr>
        <th scope="col">Name</th>
        <th scope="col">Email</th>
        <th scope="col">Address</th>
        <th scope="col">Number</th>
        <th scope="col">Needs</th>
        <th scope="col">Notes</th>
        <th scope="col">HelperID</th>
        <th scope="col">Actions</th>
      </tr>
    </thead>
    <tbody>
      {#each needies as needy}
      <tr>
        <td>{needy.name}</td>
        <td>{#if needy.needyState === "ASSIGNED" && needy.helperId === $myHelperId}{needy.email}{/if}</td>
        <td>{needy.address}</td>
        <td> {#if needy.needyState === "ASSIGNED" && needy.helperId === $myHelperId}
          {needy.number}
        {/if}</td>
        <td>{needy.needs.join(", ")}</td>
        <td>{needy.notes}</td>
        <td>{#if needy.helperId !== $myHelperId}
        <p>assigned to other person</p>
      {:else} 
      {needy.helperId} 
      {/if}
      </td>
        <td>
          {#if needy.needyState === "ASSIGNED"}
            <span class="badge bg-secondary">Assigned</span>
          {:else if needy.helperId === null}
            <button
              type="button"
              class="btn btn-primary btn-sm"
              on:click={() => { assignToNeedy(needy.id); }}
            >
              Assign to me
            </button>
          {/if}
          {#if needy.needyState === "DONE"}
            <span class="badge bg-secondary">Done</span>
          {:else if needy.needyState === "ASSIGNED" && needy.helperId === $myHelperId}
            <button
              type="button"
              class="btn btn-primary btn-sm"
              on:click={() => { completeNeedy(needy.id); }}
            >
              Complete Job
            </button>
          {/if}
        </td>
      </tr>
      {/each}
    </tbody>
  </table>

  <nav>
    <ul class="pagination">
    {#each Array(nrOfPages) as _, i}
    <li class="page-item">
    <a
    class="page-link"
    class:active={currentPage == i + 1}
    href={"#/allneedy?page=" + (i + 1)}>{i + 1}
    </a>
    </li>
    {/each}
    </ul>
    </nav>
  