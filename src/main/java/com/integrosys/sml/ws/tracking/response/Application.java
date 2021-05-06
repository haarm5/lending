/**
 * Application.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.ws.tracking.response;

public class Application  implements java.io.Serializable {
    private java.lang.String appRefNo;

    private java.lang.String appStatus;

    private java.lang.String appStatusDesc;

    private java.lang.String appType;

    private com.integrosys.sml.ws.tracking.response.Applicant[] applicants;

    private java.lang.String applicationDate;

    private java.util.Calendar balanceTransDate;

    private long caId;

    private java.lang.String currentNode;

    private java.lang.String instantAppliedStepFlag;

    private java.lang.String instantFlag;

    private java.lang.String isApproved;

    private java.lang.String isIncompleteDocUpdated;

    private java.lang.String isRejected;

    private java.lang.String isSubmitted;

    private java.util.Calendar lastUpdateDate;

    private java.lang.String natureOfRequest;

    private java.lang.String natureOfRequestDescEN;

    private java.lang.String natureOfRequestDescTH;

    private com.integrosys.sml.ws.tracking.response.RoadMap[] roadMap;

    private long taskId;

    public Application() {
    }

    public Application(
           java.lang.String appRefNo,
           java.lang.String appStatus,
           java.lang.String appStatusDesc,
           java.lang.String appType,
           com.integrosys.sml.ws.tracking.response.Applicant[] applicants,
           java.lang.String applicationDate,
           java.util.Calendar balanceTransDate,
           long caId,
           java.lang.String currentNode,
           java.lang.String instantAppliedStepFlag,
           java.lang.String instantFlag,
           java.lang.String isApproved,
           java.lang.String isIncompleteDocUpdated,
           java.lang.String isRejected,
           java.lang.String isSubmitted,
           java.util.Calendar lastUpdateDate,
           java.lang.String natureOfRequest,
           java.lang.String natureOfRequestDescEN,
           java.lang.String natureOfRequestDescTH,
           com.integrosys.sml.ws.tracking.response.RoadMap[] roadMap,
           long taskId) {
           this.appRefNo = appRefNo;
           this.appStatus = appStatus;
           this.appStatusDesc = appStatusDesc;
           this.appType = appType;
           this.applicants = applicants;
           this.applicationDate = applicationDate;
           this.balanceTransDate = balanceTransDate;
           this.caId = caId;
           this.currentNode = currentNode;
           this.instantAppliedStepFlag = instantAppliedStepFlag;
           this.instantFlag = instantFlag;
           this.isApproved = isApproved;
           this.isIncompleteDocUpdated = isIncompleteDocUpdated;
           this.isRejected = isRejected;
           this.isSubmitted = isSubmitted;
           this.lastUpdateDate = lastUpdateDate;
           this.natureOfRequest = natureOfRequest;
           this.natureOfRequestDescEN = natureOfRequestDescEN;
           this.natureOfRequestDescTH = natureOfRequestDescTH;
           this.roadMap = roadMap;
           this.taskId = taskId;
    }


    /**
     * Gets the appRefNo value for this Application.
     * 
     * @return appRefNo
     */
    public java.lang.String getAppRefNo() {
        return appRefNo;
    }


    /**
     * Sets the appRefNo value for this Application.
     * 
     * @param appRefNo
     */
    public void setAppRefNo(java.lang.String appRefNo) {
        this.appRefNo = appRefNo;
    }


    /**
     * Gets the appStatus value for this Application.
     * 
     * @return appStatus
     */
    public java.lang.String getAppStatus() {
        return appStatus;
    }


    /**
     * Sets the appStatus value for this Application.
     * 
     * @param appStatus
     */
    public void setAppStatus(java.lang.String appStatus) {
        this.appStatus = appStatus;
    }


    /**
     * Gets the appStatusDesc value for this Application.
     * 
     * @return appStatusDesc
     */
    public java.lang.String getAppStatusDesc() {
        return appStatusDesc;
    }


    /**
     * Sets the appStatusDesc value for this Application.
     * 
     * @param appStatusDesc
     */
    public void setAppStatusDesc(java.lang.String appStatusDesc) {
        this.appStatusDesc = appStatusDesc;
    }


    /**
     * Gets the appType value for this Application.
     * 
     * @return appType
     */
    public java.lang.String getAppType() {
        return appType;
    }


    /**
     * Sets the appType value for this Application.
     * 
     * @param appType
     */
    public void setAppType(java.lang.String appType) {
        this.appType = appType;
    }


    /**
     * Gets the applicants value for this Application.
     * 
     * @return applicants
     */
    public com.integrosys.sml.ws.tracking.response.Applicant[] getApplicants() {
        return applicants;
    }


    /**
     * Sets the applicants value for this Application.
     * 
     * @param applicants
     */
    public void setApplicants(com.integrosys.sml.ws.tracking.response.Applicant[] applicants) {
        this.applicants = applicants;
    }


    /**
     * Gets the applicationDate value for this Application.
     * 
     * @return applicationDate
     */
    public java.lang.String getApplicationDate() {
        return applicationDate;
    }


    /**
     * Sets the applicationDate value for this Application.
     * 
     * @param applicationDate
     */
    public void setApplicationDate(java.lang.String applicationDate) {
        this.applicationDate = applicationDate;
    }


    /**
     * Gets the balanceTransDate value for this Application.
     * 
     * @return balanceTransDate
     */
    public java.util.Calendar getBalanceTransDate() {
        return balanceTransDate;
    }


    /**
     * Sets the balanceTransDate value for this Application.
     * 
     * @param balanceTransDate
     */
    public void setBalanceTransDate(java.util.Calendar balanceTransDate) {
        this.balanceTransDate = balanceTransDate;
    }


    /**
     * Gets the caId value for this Application.
     * 
     * @return caId
     */
    public long getCaId() {
        return caId;
    }


    /**
     * Sets the caId value for this Application.
     * 
     * @param caId
     */
    public void setCaId(long caId) {
        this.caId = caId;
    }


    /**
     * Gets the currentNode value for this Application.
     * 
     * @return currentNode
     */
    public java.lang.String getCurrentNode() {
        return currentNode;
    }


    /**
     * Sets the currentNode value for this Application.
     * 
     * @param currentNode
     */
    public void setCurrentNode(java.lang.String currentNode) {
        this.currentNode = currentNode;
    }


    /**
     * Gets the instantAppliedStepFlag value for this Application.
     * 
     * @return instantAppliedStepFlag
     */
    public java.lang.String getInstantAppliedStepFlag() {
        return instantAppliedStepFlag;
    }


    /**
     * Sets the instantAppliedStepFlag value for this Application.
     * 
     * @param instantAppliedStepFlag
     */
    public void setInstantAppliedStepFlag(java.lang.String instantAppliedStepFlag) {
        this.instantAppliedStepFlag = instantAppliedStepFlag;
    }


    /**
     * Gets the instantFlag value for this Application.
     * 
     * @return instantFlag
     */
    public java.lang.String getInstantFlag() {
        return instantFlag;
    }


    /**
     * Sets the instantFlag value for this Application.
     * 
     * @param instantFlag
     */
    public void setInstantFlag(java.lang.String instantFlag) {
        this.instantFlag = instantFlag;
    }


    /**
     * Gets the isApproved value for this Application.
     * 
     * @return isApproved
     */
    public java.lang.String getIsApproved() {
        return isApproved;
    }


    /**
     * Sets the isApproved value for this Application.
     * 
     * @param isApproved
     */
    public void setIsApproved(java.lang.String isApproved) {
        this.isApproved = isApproved;
    }


    /**
     * Gets the isIncompleteDocUpdated value for this Application.
     * 
     * @return isIncompleteDocUpdated
     */
    public java.lang.String getIsIncompleteDocUpdated() {
        return isIncompleteDocUpdated;
    }


    /**
     * Sets the isIncompleteDocUpdated value for this Application.
     * 
     * @param isIncompleteDocUpdated
     */
    public void setIsIncompleteDocUpdated(java.lang.String isIncompleteDocUpdated) {
        this.isIncompleteDocUpdated = isIncompleteDocUpdated;
    }


    /**
     * Gets the isRejected value for this Application.
     * 
     * @return isRejected
     */
    public java.lang.String getIsRejected() {
        return isRejected;
    }


    /**
     * Sets the isRejected value for this Application.
     * 
     * @param isRejected
     */
    public void setIsRejected(java.lang.String isRejected) {
        this.isRejected = isRejected;
    }


    /**
     * Gets the isSubmitted value for this Application.
     * 
     * @return isSubmitted
     */
    public java.lang.String getIsSubmitted() {
        return isSubmitted;
    }


    /**
     * Sets the isSubmitted value for this Application.
     * 
     * @param isSubmitted
     */
    public void setIsSubmitted(java.lang.String isSubmitted) {
        this.isSubmitted = isSubmitted;
    }


    /**
     * Gets the lastUpdateDate value for this Application.
     * 
     * @return lastUpdateDate
     */
    public java.util.Calendar getLastUpdateDate() {
        return lastUpdateDate;
    }


    /**
     * Sets the lastUpdateDate value for this Application.
     * 
     * @param lastUpdateDate
     */
    public void setLastUpdateDate(java.util.Calendar lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }


    /**
     * Gets the natureOfRequest value for this Application.
     * 
     * @return natureOfRequest
     */
    public java.lang.String getNatureOfRequest() {
        return natureOfRequest;
    }


    /**
     * Sets the natureOfRequest value for this Application.
     * 
     * @param natureOfRequest
     */
    public void setNatureOfRequest(java.lang.String natureOfRequest) {
        this.natureOfRequest = natureOfRequest;
    }


    /**
     * Gets the natureOfRequestDescEN value for this Application.
     * 
     * @return natureOfRequestDescEN
     */
    public java.lang.String getNatureOfRequestDescEN() {
        return natureOfRequestDescEN;
    }


    /**
     * Sets the natureOfRequestDescEN value for this Application.
     * 
     * @param natureOfRequestDescEN
     */
    public void setNatureOfRequestDescEN(java.lang.String natureOfRequestDescEN) {
        this.natureOfRequestDescEN = natureOfRequestDescEN;
    }


    /**
     * Gets the natureOfRequestDescTH value for this Application.
     * 
     * @return natureOfRequestDescTH
     */
    public java.lang.String getNatureOfRequestDescTH() {
        return natureOfRequestDescTH;
    }


    /**
     * Sets the natureOfRequestDescTH value for this Application.
     * 
     * @param natureOfRequestDescTH
     */
    public void setNatureOfRequestDescTH(java.lang.String natureOfRequestDescTH) {
        this.natureOfRequestDescTH = natureOfRequestDescTH;
    }


    /**
     * Gets the roadMap value for this Application.
     * 
     * @return roadMap
     */
    public com.integrosys.sml.ws.tracking.response.RoadMap[] getRoadMap() {
        return roadMap;
    }


    /**
     * Sets the roadMap value for this Application.
     * 
     * @param roadMap
     */
    public void setRoadMap(com.integrosys.sml.ws.tracking.response.RoadMap[] roadMap) {
        this.roadMap = roadMap;
    }


    /**
     * Gets the taskId value for this Application.
     * 
     * @return taskId
     */
    public long getTaskId() {
        return taskId;
    }


    /**
     * Sets the taskId value for this Application.
     * 
     * @param taskId
     */
    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Application)) return false;
        Application other = (Application) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.appRefNo==null && other.getAppRefNo()==null) || 
             (this.appRefNo!=null &&
              this.appRefNo.equals(other.getAppRefNo()))) &&
            ((this.appStatus==null && other.getAppStatus()==null) || 
             (this.appStatus!=null &&
              this.appStatus.equals(other.getAppStatus()))) &&
            ((this.appStatusDesc==null && other.getAppStatusDesc()==null) || 
             (this.appStatusDesc!=null &&
              this.appStatusDesc.equals(other.getAppStatusDesc()))) &&
            ((this.appType==null && other.getAppType()==null) || 
             (this.appType!=null &&
              this.appType.equals(other.getAppType()))) &&
            ((this.applicants==null && other.getApplicants()==null) || 
             (this.applicants!=null &&
              java.util.Arrays.equals(this.applicants, other.getApplicants()))) &&
            ((this.applicationDate==null && other.getApplicationDate()==null) || 
             (this.applicationDate!=null &&
              this.applicationDate.equals(other.getApplicationDate()))) &&
            ((this.balanceTransDate==null && other.getBalanceTransDate()==null) || 
             (this.balanceTransDate!=null &&
              this.balanceTransDate.equals(other.getBalanceTransDate()))) &&
            this.caId == other.getCaId() &&
            ((this.currentNode==null && other.getCurrentNode()==null) || 
             (this.currentNode!=null &&
              this.currentNode.equals(other.getCurrentNode()))) &&
            ((this.instantAppliedStepFlag==null && other.getInstantAppliedStepFlag()==null) || 
             (this.instantAppliedStepFlag!=null &&
              this.instantAppliedStepFlag.equals(other.getInstantAppliedStepFlag()))) &&
            ((this.instantFlag==null && other.getInstantFlag()==null) || 
             (this.instantFlag!=null &&
              this.instantFlag.equals(other.getInstantFlag()))) &&
            ((this.isApproved==null && other.getIsApproved()==null) || 
             (this.isApproved!=null &&
              this.isApproved.equals(other.getIsApproved()))) &&
            ((this.isIncompleteDocUpdated==null && other.getIsIncompleteDocUpdated()==null) || 
             (this.isIncompleteDocUpdated!=null &&
              this.isIncompleteDocUpdated.equals(other.getIsIncompleteDocUpdated()))) &&
            ((this.isRejected==null && other.getIsRejected()==null) || 
             (this.isRejected!=null &&
              this.isRejected.equals(other.getIsRejected()))) &&
            ((this.isSubmitted==null && other.getIsSubmitted()==null) || 
             (this.isSubmitted!=null &&
              this.isSubmitted.equals(other.getIsSubmitted()))) &&
            ((this.lastUpdateDate==null && other.getLastUpdateDate()==null) || 
             (this.lastUpdateDate!=null &&
              this.lastUpdateDate.equals(other.getLastUpdateDate()))) &&
            ((this.natureOfRequest==null && other.getNatureOfRequest()==null) || 
             (this.natureOfRequest!=null &&
              this.natureOfRequest.equals(other.getNatureOfRequest()))) &&
            ((this.natureOfRequestDescEN==null && other.getNatureOfRequestDescEN()==null) || 
             (this.natureOfRequestDescEN!=null &&
              this.natureOfRequestDescEN.equals(other.getNatureOfRequestDescEN()))) &&
            ((this.natureOfRequestDescTH==null && other.getNatureOfRequestDescTH()==null) || 
             (this.natureOfRequestDescTH!=null &&
              this.natureOfRequestDescTH.equals(other.getNatureOfRequestDescTH()))) &&
            ((this.roadMap==null && other.getRoadMap()==null) || 
             (this.roadMap!=null &&
              java.util.Arrays.equals(this.roadMap, other.getRoadMap()))) &&
            this.taskId == other.getTaskId();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAppRefNo() != null) {
            _hashCode += getAppRefNo().hashCode();
        }
        if (getAppStatus() != null) {
            _hashCode += getAppStatus().hashCode();
        }
        if (getAppStatusDesc() != null) {
            _hashCode += getAppStatusDesc().hashCode();
        }
        if (getAppType() != null) {
            _hashCode += getAppType().hashCode();
        }
        if (getApplicants() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getApplicants());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getApplicants(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getApplicationDate() != null) {
            _hashCode += getApplicationDate().hashCode();
        }
        if (getBalanceTransDate() != null) {
            _hashCode += getBalanceTransDate().hashCode();
        }
        _hashCode += new Long(getCaId()).hashCode();
        if (getCurrentNode() != null) {
            _hashCode += getCurrentNode().hashCode();
        }
        if (getInstantAppliedStepFlag() != null) {
            _hashCode += getInstantAppliedStepFlag().hashCode();
        }
        if (getInstantFlag() != null) {
            _hashCode += getInstantFlag().hashCode();
        }
        if (getIsApproved() != null) {
            _hashCode += getIsApproved().hashCode();
        }
        if (getIsIncompleteDocUpdated() != null) {
            _hashCode += getIsIncompleteDocUpdated().hashCode();
        }
        if (getIsRejected() != null) {
            _hashCode += getIsRejected().hashCode();
        }
        if (getIsSubmitted() != null) {
            _hashCode += getIsSubmitted().hashCode();
        }
        if (getLastUpdateDate() != null) {
            _hashCode += getLastUpdateDate().hashCode();
        }
        if (getNatureOfRequest() != null) {
            _hashCode += getNatureOfRequest().hashCode();
        }
        if (getNatureOfRequestDescEN() != null) {
            _hashCode += getNatureOfRequestDescEN().hashCode();
        }
        if (getNatureOfRequestDescTH() != null) {
            _hashCode += getNatureOfRequestDescTH().hashCode();
        }
        if (getRoadMap() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRoadMap());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRoadMap(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += new Long(getTaskId()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Application.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "Application"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appRefNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "appRefNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "appStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appStatusDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "appStatusDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "appType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("applicants");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "applicants"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "Applicant"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://tracking.ws.sml.integrosys.com", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("applicationDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "applicationDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balanceTransDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "balanceTransDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "caId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentNode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "currentNode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("instantAppliedStepFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "instantAppliedStepFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("instantFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "instantFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isApproved");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "isApproved"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isIncompleteDocUpdated");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "isIncompleteDocUpdated"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isRejected");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "isRejected"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isSubmitted");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "isSubmitted"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastUpdateDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "lastUpdateDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("natureOfRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "natureOfRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("natureOfRequestDescEN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "natureOfRequestDescEN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("natureOfRequestDescTH");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "natureOfRequestDescTH"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("roadMap");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "roadMap"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "RoadMap"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://tracking.ws.sml.integrosys.com", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taskId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "taskId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
