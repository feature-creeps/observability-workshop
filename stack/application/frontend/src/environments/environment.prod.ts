export const environment = {
  production: true,
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
