<script>
    import { isAuthenticated, user } from "../store";
    import { jwt_token} from "../store";
    import axios from "axios";

    const api_root = window.location.origin;
    let assignedNeedies = [];

function getAssignedNeedies() {
    var config = {
        method: "get",
        url: api_root + "/api/me/assignedneedy",
        headers: { "Authorization": "Bearer " + $jwt_token },
    };

    axios(config)
        .then(function (response) {
            assignedNeedies = response.data;
        })
        .catch(function (error) {
            alert("Could not get assigned needies");
            console.log(error);
        });
}

getAssignedNeedies();

</script>

<h1>Account Details</h1>

{#if $isAuthenticated}
    <p><img src={$user.picture} alt="" srcset="" /></p>
    <p><b>Name:</b> {$user.name}</p>
    <p><b>Nickname:</b> {$user.nickname}</p>
    <p><b>Email:</b> {$user.email}</p>
    <!-- Show roles only if user has at least one role -->
    {#if $user.user_roles && $user.user_roles.length > 0}
        <p><b>Roles:</b> {$user.user_roles}</p>
    {/if}
{:else}
    <p>Not logged in</p>
{/if}

<h2>Assigned Needies</h2>
<ul>
  {#each assignedNeedies as needy}
    <li>{needy.name}</li>
  {/each}
</ul>
