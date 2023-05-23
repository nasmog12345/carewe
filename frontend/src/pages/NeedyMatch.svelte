<script>
    import axios from "axios";
    
    const api_root = "http://localhost:8080";
  
    export let needyId;
  
    let match = {
      needyId: needyId,
      helperEmail: null
    };
    
    function putMatch(){
      var config = {
        method: 'put',
        maxBodyLength: Infinity,
        url: api_root + "/api/service/assignneedy",
        headers: { },
        data : match
      };
  
      axios(config)
        .then(function (response) {
          console.log(JSON.stringify(response.data));
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  </script>
  
  <div>
    <input type="text" bind:value={match.needyId} placeholder="Needy ID">
    <input type="text" bind:value={match.helperEmail} placeholder="Helper Email">
    <button on:click={putMatch}>Match</button>
  </div>
  