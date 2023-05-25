<script>
    import axios from 'axios';
    import { isAuthenticated } from '../store';
    import {jwt_token } from "../store";
    import { querystring } from "svelte-spa-router";
  
    let helpers = [];
    let searchHelper = [];
    let helperon = false;
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
getHelpers();
}
  
    async function getHelpers() {
        let query = "?pageSize=" + defaultPageSize + " &pageNumber=" + currentPage;
        try {
            const config = {
                method: 'get',
                url: 'http://localhost:8080/api/helper' + query,
                params: {
                    address: searchAddress,
                    skills: searchHelper.join(','),
                },
                headers: {"Authorization": "Bearer " + $jwt_token,},
            };

            const response = await axios(config);

            helpers = response.data.content;
            nrOfPages = response.data.totalPages;
        } catch (error) {
            console.error('An error occurred:', error);
        }
    }

    function navigateCreateHelper() {
        location.href = '#/helper';
    }

    function navigateAllHelpers() {
        location.href = '#/allhelper';
    }


    function toggle() {
        helperon = true;
    }
</script>

<img src="/bilder/5.png" alt="img" class="angepasstes-bild">

<div class="container">
    <div class="search-section">
        <div class="search-form">
            <h1 class="mt-3">Search for Helpers</h1>
            <form>
                <div class="row mb-3">
                    <div class="col">
                        <label class="form-label" for="searchAddress">Address</label>
                        <input type="text" class="form-control" id="searchAddress" bind:value="{searchAddress}">
                        <label class="form-label" for="searchHelper">Skills</label>
                        <select bind:value="{searchHelper}" class="select-control" id="searchHelper" multiple>
                            <option value="">Leer</option>
                            <option value="Handwerker">Handwerker</option>
                            <option value="IT">IT</option>
                            <option value="KV">KV</option>
                        </select>
                    </div>
                </div>
                <button type="button" class="search-button" on:click="{getHelpers}" on:click={toggle}>Search</button>
            </form>

            <nav style="padding: 11px;">
                <ul class="pagination">
                {#each Array(nrOfPages) as _, i}
                <li class="page-item">
                <a
                class="page-link"
                class:active={currentPage == i + 1}
                href={"#/helperhome?page=" + (i + 1)}>{i + 1}
                </a>
                </li>
                {/each}
                </ul>
                </nav>
        </div>
        <div class="search-results">
            {#if helperon && $isAuthenticated}
                <button type="button" class="btn" on:click={navigateCreateHelper}>Create Helper</button>
                <button type="button" class="btn" on:click={navigateAllHelpers}>See all Helpers</button>
                <h1>All Helpers</h1>
            {/if}
            {#each helpers as helper (helper.id)}
                <div class="card">
                    <div class="card-body">
                        <ul>
                            {#if $isAuthenticated}
                                <li><a href={`#/helper/${helper.id}`}>{helper.name}</a></li>
                            {/if}
                            <li>{helper.address}</li>
                            {#if helper.skills}
                                <li>{helper.skills.join(", ")}</li>
                            {/if}
                            <li>{helper.bio}</li>
                            {#if $isAuthenticated}
                                <li>{helper.helperState}</li>
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

