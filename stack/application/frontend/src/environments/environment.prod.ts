export const environment = {
  production: true,
  backend: {
    imageorchestrator: '/proxy/imageorchestrator-service',
    imageholder: '/proxy/imageholder-service',
    imagerotator: '/proxy/imagerotator-service',
    imagegrayscale: '/proxy/imagegrayscale-service',
    imageresize: '/proxy/imageresize-service',
    imageflip: '/proxy/imageflip-service',
    imagethumbnail: '/proxy/imagethumbnail-service'
  },
  tools: {
    kibana: ':5601',
    grafana: ':3000',
    prometheus: ':9090',
    zipkin: ':9411',
  }
};
