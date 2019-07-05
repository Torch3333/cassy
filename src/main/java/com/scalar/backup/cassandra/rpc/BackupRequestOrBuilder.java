// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cassandra-backup.proto

package com.scalar.backup.cassandra.rpc;

public interface BackupRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:rpc.BackupRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string cluster_id = 1;</code>
   */
  java.lang.String getClusterId();
  /**
   * <code>string cluster_id = 1;</code>
   */
  com.google.protobuf.ByteString
      getClusterIdBytes();

  /**
   * <pre>
   * optional
   * </pre>
   *
   * <code>string target_ip = 2;</code>
   */
  java.lang.String getTargetIp();
  /**
   * <pre>
   * optional
   * </pre>
   *
   * <code>string target_ip = 2;</code>
   */
  com.google.protobuf.ByteString
      getTargetIpBytes();

  /**
   * <pre>
   * optional
   * </pre>
   *
   * <code>string snapshot_id = 3;</code>
   */
  java.lang.String getSnapshotId();
  /**
   * <pre>
   * optional
   * </pre>
   *
   * <code>string snapshot_id = 3;</code>
   */
  com.google.protobuf.ByteString
      getSnapshotIdBytes();

  /**
   * <code>uint32 backup_type = 4;</code>
   */
  int getBackupType();
}
