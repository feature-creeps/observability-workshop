// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  backend: {
    imageorchestrator: 'http://46.101.170.74:8080',
    imageholder: 'http://46.101.170.74:8081',
    imagerotator: 'http://46.101.170.74:8082',
    imagegrayscale: 'http://46.101.170.74:8083',
    imageresize: 'http://46.101.170.74:8084',
    imageflip: 'http://46.101.170.74:8085',
    imagethumbnail: 'http://46.101.170.74:8086'
  },
  tools: {
    kibana: 'http://46.101.170.74:5601',
    grafana: 'http://46.101.170.74:3000',
    prometheus: 'http://46.101.170.74:9090',
    zipkin: 'http://46.101.170.74:9411',
  }
};
