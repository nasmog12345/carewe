<script>
  import { onMount } from "svelte";
  import axios from "axios";
  import { jwt_token} from "../store";
  import { querystring } from "svelte-spa-router";

  const api_root = window.location.origin;

  let helpers = [];

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
getHelpers();
}


  function getHelpers() {
    let query = "?pageSize=" + defaultPageSize + " &pageNumber=" + currentPage;
  var config = {
      method: 'get',
      url: api_root + "/api/helper" + query,
      headers: {
      "Authorization": "Bearer " + $jwt_token,
    },
  };

  axios(config)
  .then(function (response) {
      helpers = response.data.content;
      nrOfPages = response.data.totalPages;
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

/*
  onMount(() => {
    getHelpers();
  });*/
  
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

<nav>
  <ul class="pagination">
  {#each Array(nrOfPages) as _, i}
  <li class="page-item">
  <a
  class="page-link"
  class:active={currentPage == i + 1}
  href={"#/allhelper?page=" + (i + 1)}>{i + 1}
  </a>
  </li>
  {/each}
  </ul>
  </nav>