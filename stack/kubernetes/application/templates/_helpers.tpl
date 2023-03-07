{{- define "dima-imageflip.service.name" -}}
    {{ .Release.Name }}-imageflip-service
{{- end -}}
{{- define "dima-imagegrayscale.service.name" -}}
    {{ .Release.Name }}-imagegrayscale-service
{{- end -}}
{{- define "dima-imageholder.service.name" -}}
    {{ .Release.Name }}-imageholder-service
{{- end -}}
{{- define "dima-imageorchestrator.service.name" -}}
    {{ .Release.Name }}-imageorchestrator-service
{{- end -}}
{{- define "dima-imagerotator.service.name" -}}
    {{ .Release.Name }}-imagerotator-service
{{- end -}}
{{- define "dima-imagethumbnail.service.name" -}}
    {{ .Release.Name }}-imagethumbnail-service
{{- end -}}
{{- define "dima-trafficgen.service.name" -}}
    {{ .Release.Name }}-trafficgen-service
{{- end -}}
{{- define "dima-mongodb.service.name" -}}
    {{ .Release.Name }}-mongodb-service
{{- end -}}
