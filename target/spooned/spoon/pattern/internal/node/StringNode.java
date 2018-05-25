package spoon.pattern.internal.node;


public class StringNode extends spoon.pattern.internal.node.AbstractPrimitiveMatcher {
    private final java.lang.String stringValueWithMarkers;

    private final java.util.Map<java.lang.String, spoon.pattern.internal.parameter.ParameterInfo> tobeReplacedSubstrings = new java.util.LinkedHashMap<>();

    private spoon.pattern.internal.parameter.ParameterInfo[] params;

    private java.util.regex.Pattern regExpPattern;

    public StringNode(java.lang.String stringValueWithMarkers) {
        this.stringValueWithMarkers = stringValueWithMarkers;
    }

    private java.lang.String getStringValueWithMarkers() {
        return stringValueWithMarkers;
    }

    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    public <T> void generateTargets(spoon.pattern.internal.DefaultGenerator generator, spoon.pattern.internal.ResultHolder<T> result, spoon.support.util.ImmutableMap parameters) {
        java.lang.Class<?> requiredClass = result.getRequiredClass();
        if ((requiredClass != null) && ((requiredClass.isAssignableFrom(java.lang.String.class)) == false)) {
            throw new spoon.SpoonException(("StringValueResolver provides only String values. It doesn't support: " + requiredClass));
        }
        java.lang.String stringValue = getStringValueWithMarkers();
        for (java.util.Map.Entry<java.lang.String, spoon.pattern.internal.parameter.ParameterInfo> requests : tobeReplacedSubstrings.entrySet()) {
            spoon.pattern.internal.parameter.ParameterInfo param = requests.getValue();
            java.lang.String replaceMarker = requests.getKey();
            spoon.pattern.internal.ResultHolder.Single<java.lang.String> ctx = new spoon.pattern.internal.ResultHolder.Single<>(java.lang.String.class);
            generator.getValueAs(param, ctx, parameters);
            java.lang.String substrValue = ((ctx.getResult()) == null) ? "" : ctx.getResult();
            stringValue = substituteSubstring(stringValue, replaceMarker, substrValue);
        }
        result.addResult(((T) (stringValue)));
    }

    @java.lang.Override
    public spoon.support.util.ImmutableMap matchTarget(java.lang.Object target, spoon.support.util.ImmutableMap parameters) {
        if ((target instanceof java.lang.String) == false) {
            return null;
        }
        java.lang.String targetString = ((java.lang.String) (target));
        java.util.regex.Pattern re = getMatchingPattern();
        java.util.regex.Matcher m = re.matcher(targetString);
        if ((m.matches()) == false) {
            return null;
        }
        spoon.pattern.internal.parameter.ParameterInfo[] params = getMatchingParameterInfos();
        for (int i = 0; i < (params.length); i++) {
            java.lang.String paramValue = m.group((i + 1));
            parameters = params[i].addValueAs(parameters, paramValue);
            if (parameters == null) {
                return null;
            }
        }
        return parameters;
    }

    public spoon.pattern.internal.parameter.ParameterInfo getParameterInfo(java.lang.String replaceMarker) {
        return tobeReplacedSubstrings.get(replaceMarker);
    }

    public void setReplaceMarker(java.lang.String replaceMarker, spoon.pattern.internal.parameter.ParameterInfo param) {
        tobeReplacedSubstrings.put(replaceMarker, param);
    }

    public java.util.Map<java.lang.String, spoon.pattern.internal.parameter.ParameterInfo> getReplaceMarkers() {
        return java.util.Collections.unmodifiableMap(tobeReplacedSubstrings);
    }

    @java.lang.Override
    public void forEachParameterInfo(java.util.function.BiConsumer<spoon.pattern.internal.parameter.ParameterInfo, spoon.pattern.internal.node.RootNode> consumer) {
        java.util.Map<spoon.pattern.internal.parameter.ParameterInfo, java.lang.Boolean> visitedParams = new java.util.IdentityHashMap<>(tobeReplacedSubstrings.size());
        for (spoon.pattern.internal.parameter.ParameterInfo parameterInfo : tobeReplacedSubstrings.values()) {
            if ((visitedParams.put(parameterInfo, java.lang.Boolean.TRUE)) == null) {
                consumer.accept(parameterInfo, this);
            }
        }
    }

    private spoon.pattern.internal.parameter.ParameterInfo[] getMatchingParameterInfos() {
        getMatchingPattern();
        return params;
    }

    private java.util.List<spoon.pattern.internal.node.StringNode.Region> getRegions() {
        java.util.List<spoon.pattern.internal.node.StringNode.Region> regions = new java.util.ArrayList<>();
        for (java.util.Map.Entry<java.lang.String, spoon.pattern.internal.parameter.ParameterInfo> markers : tobeReplacedSubstrings.entrySet()) {
            addRegionsOf(regions, markers.getValue(), markers.getKey());
        }
        regions.sort(( a, b) -> (a.from) - (b.from));
        return regions;
    }

    private synchronized java.util.regex.Pattern getMatchingPattern() {
        if ((regExpPattern) == null) {
            java.util.List<spoon.pattern.internal.node.StringNode.Region> regions = getRegions();
            java.lang.StringBuilder re = new java.lang.StringBuilder();
            java.util.List<spoon.pattern.internal.parameter.ParameterInfo> paramsByRegions = new java.util.ArrayList<>();
            int start = 0;
            for (spoon.pattern.internal.node.StringNode.Region region : regions) {
                if ((region.from) > start) {
                    re.append(escapeRegExp(getStringValueWithMarkers().substring(start, region.from)));
                }else
                    if (start > 0) {
                        throw new spoon.SpoonException(("Cannot detect string parts if parameter separators are missing in pattern value: " + (getStringValueWithMarkers())));
                    }

                re.append("(").append(".*?").append(")");
                paramsByRegions.add(region.param);
                start = region.to;
            }
            if (start < (getStringValueWithMarkers().length())) {
                re.append(escapeRegExp(getStringValueWithMarkers().substring(start)));
            }
            regExpPattern = java.util.regex.Pattern.compile(re.toString());
            params = paramsByRegions.toArray(new spoon.pattern.internal.parameter.ParameterInfo[paramsByRegions.size()]);
        }
        return regExpPattern;
    }

    private static class Region {
        spoon.pattern.internal.parameter.ParameterInfo param;

        int from;

        int to;

        Region(spoon.pattern.internal.parameter.ParameterInfo param, int from, int to) {
            super();
            this.param = param;
            this.from = from;
            this.to = to;
        }
    }

    private void addRegionsOf(java.util.List<spoon.pattern.internal.node.StringNode.Region> regions, spoon.pattern.internal.parameter.ParameterInfo param, java.lang.String marker) {
        int start = 0;
        while (start < (getStringValueWithMarkers().length())) {
            start = getStringValueWithMarkers().indexOf(marker, start);
            if (start < 0) {
                return;
            }
            regions.add(new spoon.pattern.internal.node.StringNode.Region(param, start, (start + (marker.length()))));
            start += marker.length();
        } 
    }

    private java.lang.String substituteSubstring(java.lang.String str, java.lang.String tobeReplacedSubstring, java.lang.String substrValue) {
        return str.replaceAll(escapeRegExp(tobeReplacedSubstring), escapeRegReplace(substrValue));
    }

    private java.lang.String escapeRegExp(java.lang.String str) {
        return ("\\Q" + str) + "\\E";
    }

    private java.lang.String escapeRegReplace(java.lang.String str) {
        return str.replaceAll("\\$", "\\\\\\$");
    }

    @java.lang.Override
    public boolean replaceNode(spoon.pattern.internal.node.RootNode oldNode, spoon.pattern.internal.node.RootNode newNode) {
        return false;
    }

    @java.lang.Override
    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int off = 0;
        for (spoon.pattern.internal.node.StringNode.Region region : getRegions()) {
            if ((region.from) > off) {
                sb.append(getStringValueWithMarkers().substring(off, region.from));
            }
            sb.append("${").append(region.param.getName()).append("}");
            off = region.to;
        }
        if ((getStringValueWithMarkers().length()) > off) {
            sb.append(getStringValueWithMarkers().substring(off));
        }
        return sb.toString();
    }

    public static spoon.pattern.internal.node.StringNode setReplaceMarker(spoon.pattern.internal.node.RootNode targetNode, java.lang.String replaceMarker, spoon.pattern.internal.parameter.ParameterInfo param) {
        spoon.pattern.internal.node.StringNode stringNode = null;
        if (targetNode instanceof spoon.pattern.internal.node.ConstantNode) {
            spoon.pattern.internal.node.ConstantNode constantNode = ((spoon.pattern.internal.node.ConstantNode) (targetNode));
            if ((constantNode.getTemplateNode()) instanceof java.lang.String) {
                stringNode = new spoon.pattern.internal.node.StringNode(((java.lang.String) (constantNode.getTemplateNode())));
            }
        }else
            if (targetNode instanceof spoon.pattern.internal.node.StringNode) {
                stringNode = ((spoon.pattern.internal.node.StringNode) (targetNode));
            }

        if (stringNode == null) {
            throw new spoon.SpoonException("Cannot add StringNode");
        }
        stringNode.setReplaceMarker(replaceMarker, param);
        return stringNode;
    }

    @java.lang.Override
    public spoon.pattern.Quantifier getMatchingStrategy() {
        return spoon.pattern.Quantifier.POSSESSIVE;
    }

    @java.lang.Override
    public boolean isTryNextMatch(spoon.support.util.ImmutableMap parameters) {
        return false;
    }
}

