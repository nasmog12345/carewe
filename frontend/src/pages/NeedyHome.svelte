<script>
  import axios from 'axios';
  import { isAuthenticated } from '../store';
  import { querystring } from "svelte-spa-router";

  let needies = [];
  let searchNeeds = [];
  let needyon = true;
  let searchAddress = null;

  let currentPage;
let nrOfPages = 0;
let defaultPageSize = 4;

$: {
let searchParams = new URLSearchParams($querystring);
if (searchParams.has("page")) {
currentPage = searchParams.get("page");
} else {
currentPage = "1";
}
getNeedies();
}

  async function getNeedies() {
    let query = "?pageSize=" + defaultPageSize + "&pageNumber=" + currentPage;
      var config = {
          method: "get",
          url: "http://localhost:8080/api/needy" + query,
          params: {
              address: searchAddress,
              needs: searchNeeds.join(',')
          }
      };

      try {
          const response = await axios(config);
          needies = response.data.content;
          nrOfPages = response.data.totalPages;
      } catch (error) {
          console.error('An error occurred:', error);
      }
  }

  function navigateCreateNeedy() {
    location.href = '#/needy';
  }

  function navigateAllNeedy() {
    location.href = '#/allneedy';
  }

  function toggle() {
    needyon = !needyon;
  }
</script>
  
  <div class="container">
    <div class="search-section">
      <div class="search-form">
        <h1 class="mt-3">Search for Needies</h1>
        <form>
          <div class="row mb-3">
            <div class="col">
              <label class="form-label" for="searchAddress">Address</label>
              <input type="text" class="form-control" id="searchAddress" bind:value="{searchAddress}">
              <label class="form-label" for="searchNeeds">Needs</label>
              <select bind:value="{searchNeeds}" class="select-control" id="searchNeeds" multiple>
                <option value="">Leer</option>
                <option value="food">Food</option>
                <option value="water">Water</option>
                <option value="shelter">Shelter</option>
                <!-- Add more options as needed -->
              </select>
            </div>
          </div>
          <button type="button" class="search-button" on:click="{getNeedies}" on:click={toggle}>Search</button>
        </form>
        <nav style="padding: 11px;">
          <ul class="pagination">
          {#each Array(nrOfPages) as _, i}
          <li class="page-item">
          <a
          class="page-link"
          class:active={currentPage == i + 1}
          href={"#/needyhome?page=" + (i + 1)}>{i + 1}
          </a>
          </li>
          {/each}
          </ul>
          </nav>
      </div>
      
      
      <div class="search-results">
        {#if needyon && $isAuthenticated}
        <button type="button" class="btn" on:click={navigateCreateNeedy}>Create Needy</button>
        <button type="button" class="btn" on:click={navigateAllNeedy}>See all Needies</button>
        
        
        <h1>All Needies</h1>
        {/if}
        {#each needies as needy (needy.id)}
        <div class="card">
          <div class="card-body">
            <ul>
              {#if $isAuthenticated}
              <li><a href={`#/needy/${needy.id}`}>{needy.name}</a></li>
              {/if}
              <li>{needy.address}</li>
              <li>{needy.needs.join(", ")}</li>
              <li>{needy.notes}</li>
              {#if $isAuthenticated}
              <li>{needy.needyState}</li>
              {/if}
            </ul>
    
            {#if !$isAuthenticated}
      
              <p>Please log in to see the full card</p>
            {/if}
          </div>
        </div>
        {/each}
      </div>
    </div>
  </div>
  
