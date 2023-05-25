<script>
    import { onMount } from 'svelte';
    import axios from 'axios';
    
    let weatherData = null;
    
    onMount(async () => {
        const response = await axios.get('https://api.open-meteo.com/v1/forecast', {
            params: {
                latitude: 47.37,
                longitude: 8.55,
                daily: 'temperature_2m_max,temperature_2m_min,sunrise,sunset',
                start_date: '2023-05-24',
                end_date: '2023-06-09',
                timezone: 'Europe/Berlin',
            },
        });
        weatherData = response.data;
    });
    </script>
    
    <style>
      .weather-card {
        border: 1px solid gray;
        margin: 1em;
        padding: 1em;
      }
    </style>

    <p>Sie befinden sich derzeit in der Umgebung von Zürich und haben eine Arbeit im Freien zu erledigen? Hier finden Sie Informationen zum aktuellen Wetter in dieser Region.</p>
    
    {#if weatherData}
      {#each weatherData.daily.time as day, index}
        <div class="weather-card">
          <h2>{day}</h2>
          <p>Max Temp: {weatherData.daily.temperature_2m_max[index]}°C</p>
          <p>Min Temp: {weatherData.daily.temperature_2m_min[index]}°C</p>
          <p>Sunrise: {weatherData.daily.sunrise[index]}</p>
          <p>Sunset: {weatherData.daily.sunset[index]}</p>
        </div>
      {/each}
    {:else}
      <p>Loading weather data...</p>
    {/if}
    