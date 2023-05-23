<script>
  import axios from 'axios';
  import HelperCard from './HelperCard.svelte';
  import Rating from './Rating.svelte';

  const api_root = 'http://localhost:8080';
  const urlParam = window.location.href.split('/')[5];

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
    var config = {
      method: 'get',
      maxBodyLength: Infinity,
      url: api_root +`/api/helper/${urlParam}`,
      headers: {},
    };
  
    axios(config)
      .then(function (response) {
        helper.id = response.data.id
        helper.name = response.data.name
        helper.email = response.data.email
        response.data.skills.forEach(element => {
          helper.skills.push(element)
        });
        helper.bio = response.data.bio

      })
      .catch(function (error) {
        console.log(error);
      });
  }

  
  
  // call the getHelper function to populate the helper object
  getHelpers();

  console.log(helper.skills)
</script>


<h1>{helper.id}</h1>
<h2>{helper.name}</h2>
<h2>{helper.email}</h2>
{#each helper.skills as skill }
  <li>{skill}</li>
{/each}
<h2>{helper.bio}</h2>

<Rating/>

<!-- profil ung so erstellen:
auf der rechten seite infos wie name usw (abgegerenzt ) durch einen dunklen balken. 
neben dem balken weisse fläche mit bio (text) und rating. 

-->


