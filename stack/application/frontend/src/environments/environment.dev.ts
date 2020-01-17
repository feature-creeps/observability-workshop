// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  backend: {
    imageorchestrator: '/proxy/imageorchestrator',
    imageholder: '/proxy/imageholder',
    imagerotator: '/proxy/imagerotator',
    imagegrayscale: '/proxy/imagegrayscale',
    imageresize: '/proxy/imageresize',
    imageflip: '/proxy/imageflip',
    imagethumbnail: '/proxy/imagethumbnail'
  },
  tools: {
    kibana: ':5601',
    grafana: ':3000',
    prometheus: ':9090',
    zipkin: ':9411',
  }
};
