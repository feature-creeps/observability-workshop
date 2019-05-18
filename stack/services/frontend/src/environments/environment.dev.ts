// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  backend: {
    imageorchestrator: 'http://localhost:8080',
    imageholder: 'http://localhost:8081',
    imagerotator: 'http://localhost:8082',
    imagegrayscale: 'http://localhost:8083',
    imageresize: 'http://localhost:8084',
    imageflip: 'http://localhost:8085',
    imagethumbnail: 'http://localhost:8086'
  },
  tools: {
    kibana: 'http://kibana.localhost',
    grafana: 'http://grafana.localhost',
    prometheus: 'http://prometheus.localhost',
    zipkin: 'http://zipkin.localhost',
  }
};
