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

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
