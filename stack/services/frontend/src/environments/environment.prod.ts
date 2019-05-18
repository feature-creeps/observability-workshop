export const environment = {
  production: true,
  backend: {
    imageorchestrator: 'proxy/imageorchestrator',
    imageholder: 'proxy/imageholder',
    imagerotator: 'proxy/imagerotator',
    imagegrayscale: 'proxy/imagegrayscale',
    imageflip: 'proxy/imageflip',
    imageresize: 'proxy/imageresize',
    imagethumbnail: 'proxy/imagethumbnail'
  },
  tools: {
    kibana: 'tool/kibana',
    grafana: 'tool/grafana',
    prometheus: 'tool/prometheus',
    zipkin: 'tool/zipkin',
  }
};
