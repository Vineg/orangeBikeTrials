public class MethodComparator implements Comparator<Method> {
    @Override
    public int compare(Method o1, Method o2) {
        int cname = o1.getName().compareTo(o2.getName());
        if (cname == 0) {
            if (o1.getParameterCount() == o2.getParameterCount()) {
                for (int i = 0; i < o1.getParameterCount(); i++) {
                    int cmethod = MethodTools.compare(o1.getParameterTypes()[i], o2.getParameterTypes()[i]);
                    if (cmethod == 0) {
                        continue;
                    } else {
                        return cmethod;
                    }
                }
            } else {
                return o1.getParameterCount() > o2.getParameterCount() ? 1 : -1;
            }
        } else {

            return cname;
        }
        return 0;
    }
}