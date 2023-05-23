<script>
    import axios from "axios";
    import { jwt_token} from "../store";

    // Update this to point to the URL of your Spring Boot backend
    const api_root = "http://localhost:8080";

    let needies = [];
    let needy = {
        name: null,
        email: null,
        address: null,
        number: null,
        needs: [],
        notes:null,
        needyState: null,
        
    };

    function getNeedies() {
        var config = {
            method: "get",
            url: api_root + "/api/needy",
            headers: {"Authorization": "Bearer " + $jwt_token,},
        };

        axios(config)
            .then(function (response) {
                needies = response.data;
            })
            .catch(function (error) {
                alert("Could not get needies");
                console.log(error);
            });
    }
    getNeedies();

    function createNeedy() {
    var config = {
        method: "post",
        url: api_root + "/api/needy",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + $jwt_token,
        },
        data: needy,
    };

        axios(config)
            .then(function (response) {
                alert("Needy created");
                getNeedies();
            })
            .catch(function (error) {
                alert("Could not create Needy");
                console.log(error);
            });
    }
</script>



<h1 class="mt-3">Create Needy</h1>
<form class="mb-5">
    <div class="row mb-3">
        <div class="col">
            <label class="form-label" for="name">Name</label>
            <input
                bind:value={needy.name}
                class="form-control"
                id="name"
                type="text"
            />
        </div>
        <div class="col">
            <label class="form-label" for="email">Email</label>
            <input
                bind:value={needy.email}
                class="form-control"
                id="email"
                type="email"
            />
        </div>
    </div>
    <div class="row mb-3">
        <div class="col">
            <label class="form-label" for="address">Stadt</label>
            <input
                bind:value={needy.address}
                class="form-control"
                id="address"
                type="text"
            />
        </div>
        <div class="col">
            <label class="form-label" for="number">Number</label>
            <input
                bind:value={needy.number}
                class="form-control"
                id="number"
                type="text"
            />
        </div>
    </div>
  
  <div class="row mb-3">
  <div class="col">
    <label class="form-label" for="needs">Needs</label>
    <select
      bind:value={needy.needs}
      class="form-control"
      id="needs"
      multiple
    >
      <option value="food">Food</option>
      <option value="water">Water</option>
      <option value="shelter">Shelter</option>
      <option value="clothing">Clothing</option>
      <option value="medical">Medical Supplies</option>
      <option value="hygiene">Hygiene Products</option>
      <option value="bedding">Bedding</option>
      <option value="furniture">Furniture</option>
      <option value="transportation">Transportation</option>
      <option value="childcare">Childcare</option>
      <option value="petcare">Pet Care</option>
    </select>
  </div>
</div>
<div class="row mb-3">
    <div class="col">
      <label class="form-label" for="bio">Notes</label>
      <textarea
        bind:value={needy.notes}
        class="form-control"
        id="bio"
        rows="5"
      ></textarea>
    </div>
  </div>

<div class="row mb-3">
    <div class="col">
        <label class="form-label" for="type">NeedyState</label>
        <select
            bind:value={needy.needyState}
            class="form-select"
            id="needyState"
            type="text"
        >
            <option value="ASSIGNED">ASSIGNED</option>
            <option value="UNALLOCATED">UNALLOCATED</option>
            <option value="DONE">DONE</option>
            </select>
    </div>
  

    <button type="button" class="btn btn-primary" on:click={createNeedy}>Submit</button>
</form>

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
        </tr>
    </thead>
    <tbody>
        {#each needies as needy}
        <tr>
        <td>{needy.name}</td>
        <td>{needy.email}</td>
        <td>{needy.address}</td>
        <td>{needy.number}</td>
        <td>{needy.needs.join(", ")}</td>
        <td>{needy.notes}</td>
        </tr>
        {/each}
        </tbody>
        </table>
